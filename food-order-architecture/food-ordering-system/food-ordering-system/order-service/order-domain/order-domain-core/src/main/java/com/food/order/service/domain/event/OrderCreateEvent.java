package com.food.order.service.domain.event;

import com.food.order.domain.event.DomainEvent;
import com.food.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreateEvent extends OrderEvent {
    public OrderCreateEvent(Order order,ZonedDateTime createdAt) {
        super(order,createdAt);
    }
}
