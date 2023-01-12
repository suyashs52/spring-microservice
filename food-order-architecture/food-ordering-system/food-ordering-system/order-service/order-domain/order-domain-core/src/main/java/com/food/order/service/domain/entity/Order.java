package com.food.order.service.domain.entity;

import com.food.order.domain.entity.AggregateRoot;
import com.food.order.domain.valueobject.*;
import com.food.order.service.domain.exception.OrderDomainException;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final ResturantId resturantId;
    private final StreetAddress streetAddress;
    private final Money price;
    private final List<OrderItem> items;

    private  TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessage;

    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()) );
        trackingId=new TrackingId(UUID.randomUUID());
        orderStatus=OrderStatus.PENDING;
        initializeOrderItems();

    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }



    private void validateItemsPrice() {

      Money orderItemsTotal = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO,Money::add); //sum the items

        if(!price.equals(orderItemsTotal)==false){
            throw new OrderDomainException("Total price: "+price.getAmount()
            + " is not equal to Order items total: "+orderItemsTotal.getAmount()+"!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if(!orderItem.isPriceValid()){
            throw new OrderDomainException("Order item price: "+orderItem.getPrice().getAmount()
            + " is not valid for product "+orderItem.getProduct().getId().getValue());
        }
    }

    private void validateTotalPrice() {
        if(price==null || !price.isGreaterThenZero()){
            throw  new OrderDomainException("Totoal price must be greater than zero!");
        }
    }

    private void validateInitialOrder() {
        if(orderStatus!=null || getId()!=null){
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }
    }


    public void pay(){
        if(orderStatus!=OrderStatus.PENDING){
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
        orderStatus=OrderStatus.PAID;
    }

    public void approve(){
        if(orderStatus!=OrderStatus.PAID){
            throw new OrderDomainException("Order is not in correct state for approve operation!");
        }
        orderStatus=OrderStatus.APPROVED;
    }

    private void initCancel(){
        if(orderStatus!=OrderStatus.PAID){
            throw new OrderDomainException("Order is not in correct state for initCancel operation");

        }
        orderStatus=OrderStatus.CANCELLED;
    }

    private void cancel(){
        if(!(orderStatus==OrderStatus.CANCELLING || orderStatus==OrderStatus.PENDING)){
            throw new OrderDomainException("Order is not in correct state for cancel operation");
        }
        orderStatus=OrderStatus.CANCELLED;
    }

    private void initializeOrderItems() {
        long itermId=1;
        for(OrderItem orderItem:items){
            orderItem.initializeOrderItem(super.getId(),new OrderItemId(itermId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        resturantId = builder.resturantId;
        streetAddress = builder.streetAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessage = builder.failureMessage;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public ResturantId getResturantId() {
        return resturantId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessage() {
        return failureMessage;
    }


    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private ResturantId resturantId;
        private StreetAddress streetAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessage;

        private Builder() {
        }


        public Builder id(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder resturantId(ResturantId val) {
            resturantId = val;
            return this;
        }

        public Builder streetAddress(StreetAddress val) {
            streetAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessage(List<String> val) {
            failureMessage = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
