package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.InventoryResponseDto;
import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.event.OrderPlacedEvent;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToOrderLineItems)
                .toList();
        Order order = Order.builder()
                .orderLineItemsList(orderLineItemsList)
                .oderNumber(UUID.randomUUID().toString())
                .build();

        //Map to list of skuCode
        List<String> skuCodeList = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        //call inventory isInStock method
        List<InventoryResponseDto> inventoryResponseDtoList= webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodeList).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<InventoryResponseDto>>(){}).block();

        boolean checkAvailable = inventoryResponseDtoList.stream().allMatch(InventoryResponseDto::isInStock);

        if(checkAvailable) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOderNumber()));
            log.info("order has been placed order ID {}",order.getId());
            return "Order Placed";
        }
        else
            throw new IllegalArgumentException("Item is not available wait for some time");

    }
    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto){
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }
}
