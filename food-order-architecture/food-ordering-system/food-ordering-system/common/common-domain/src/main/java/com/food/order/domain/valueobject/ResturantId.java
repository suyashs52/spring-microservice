package com.food.order.domain.valueobject;

import java.util.UUID;

public class ResturantId extends BaseId<UUID> {

    protected ResturantId(UUID value) {
        super(value);
    }
}
