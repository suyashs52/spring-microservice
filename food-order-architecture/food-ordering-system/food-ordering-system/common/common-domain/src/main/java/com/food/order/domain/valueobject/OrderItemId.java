package com.food.order.domain.valueobject;

import com.food.order.domain.entity.BaseEntity;

import java.util.UUID;

public class OrderItemId extends BaseEntity<Long> {

    public OrderItemId(Long value) {
        super.setId(value);
    }
}
