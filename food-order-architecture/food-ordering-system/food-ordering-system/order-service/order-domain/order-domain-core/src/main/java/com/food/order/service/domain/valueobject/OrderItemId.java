package com.food.order.service.domain.valueobject;

import com.food.order.domain.entity.BaseEntity;

public class OrderItemId extends BaseEntity<Long> {

    public OrderItemId(Long value) {
        super.setId(value);
    }
}
