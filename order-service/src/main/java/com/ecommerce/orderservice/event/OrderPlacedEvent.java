package com.ecommerce.orderservice.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPlacedEvent {
    private String orderNumber;
}
