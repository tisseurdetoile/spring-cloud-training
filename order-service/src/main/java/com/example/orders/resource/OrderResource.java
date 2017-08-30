package com.example.orders.resource;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.example.orders.domain.Item;
import com.example.orders.domain.Order;
import com.example.orders.domain.OrderState;

public class OrderResource  extends ResourceSupport {

    private OrderState state = OrderState.CART;

    private LocalDateTime paidAt;

    private LocalDateTime shippedAt;

    public OrderResource() {}
    
    public OrderResource(Order order) {
        this.state = order.getState();
        this.paidAt = order.getPaidAt();
        this.shippedAt = order.getShippedAt();
    }


    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(LocalDateTime shippedAt) {
        this.shippedAt = shippedAt;
    }

}
