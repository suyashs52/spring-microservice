package com.food.order.service.domain;

import com.food.order.service.domain.entity.Order;
import com.food.order.service.domain.entity.Product;
import com.food.order.service.domain.entity.Resturant;
import com.food.order.service.domain.event.OrderCancelledEvent;
import com.food.order.service.domain.event.OrderCreateEvent;
import com.food.order.service.domain.event.OrderPaidEvent;
import com.food.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{
    
    private static final String UTC="UTC";
    
    @Override
    public OrderCreateEvent validateandInitiateOrder(Order order, Resturant resturant) {
        validateResturant(resturant);
        setOrderProductInformation(order,resturant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id {} is initialized",order.getId().getValue());
        return new OrderCreateEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }


    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id : {} is paid",order.getId().getValue());
        return new OrderPaidEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id : {} is approved",order.getId().getValue());

    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order,List<String> failureMessage) {
        order.initCancel(failureMessage);
        log.info("Order payment is cancelling",order.getId().getValue());
        return new OrderCancelledEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
        log.info("Order with id {} is cancelled",order.getId().getValue());
    }

    private void setOrderProductInformation(Order order, Resturant resturant) {
        order.getItems().forEach(orderItem -> resturant.getProducts().forEach(resturantProduct->{
            Product currentProduct=orderItem.getProduct();
            if(currentProduct.equals(resturantProduct)){
                currentProduct.updateWithConfirmedNameAndPrice(resturantProduct.getName(),resturantProduct.getPrice());
            }
        }));
    }
    private void validateResturant(Resturant resturant){
        if(!resturant.isActive()){
            throw new OrderDomainException("Resturant with id "+resturant.getId().getValue()
            + " is currently not active");
        }
    }

}
