package com.food.order.service.domain.entity;

import com.food.order.domain.entity.AggregateRoot;
import com.food.order.domain.valueobject.ResturantId;

import java.util.List;

public class Resturant extends AggregateRoot<ResturantId> {

    private ResturantId resturantId;
    private final List<Product> products;
    private boolean active;

    private Resturant(Builder builder) {
        super.setId(builder.resturantId);
        products = builder.products;
        active = builder.active;
    }
    public static Builder builder() {
        return new Builder();
    }

    public ResturantId getResturantId() {
        return resturantId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private ResturantId resturantId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }



        public Builder id(ResturantId val) {
            resturantId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Resturant build() {
            return new Resturant(this);
        }
    }
}
