package com.food.order.service.domain.event;

import com.food.order.domain.event.DomainEvent;
import com.food.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent  extends OrderEvent{


    public OrderCancelledEvent(Order order, ZonedDateTime createAt) {
         super(order,createAt);
    }

}
