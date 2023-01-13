package com.food.order.service.domain;

import com.food.order.service.domain.entity.Order;
import com.food.order.service.domain.entity.Resturant;
import com.food.order.service.domain.event.OrderCancelledEvent;
import com.food.order.service.domain.event.OrderCreateEvent;
import com.food.order.service.domain.event.OrderPaidEvent;

import java.util.List;
//IN application service, domain layer should not know how to fire the event
public interface OrderDomainService {
   OrderCreateEvent validateandInitiateOrder(Order order, Resturant resturant);
   OrderPaidEvent payOrder(Order order);
   void approveOrder(Order order);
  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);
   void cancelOrder(Order order, List<String> failureMessage);

}
