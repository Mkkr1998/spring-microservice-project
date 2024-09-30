package com.ecommerce.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }
    static {
        mongoDBContainer.start();
    }
    @Test
    public void createProductTest() throws Exception{
        ProductRequest productRequest = getProductRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Assertions.assertThat(productRepo.findAll().size()).isEqualTo(1);
    }

    private ProductRequest getProductRequest(){
        return ProductRequest.builder()
                .description("iphone 15")
                .name("iPhone 13")
                .price(BigDecimal.valueOf(56000))
                .build();
    }
}
