package com.ecommerce.notificationservice;

import com.ecommerce.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class);
    }

    @KafkaListener(topics="notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlaceEvent){
        log.info("Received notification for order no");

    }
}