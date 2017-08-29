package com.example.orders.resource;


import com.example.orders.domain.Item;
import com.example.orders.domain.Order;
import com.example.orders.domain.OrderState;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderResource  extends ResourceSupport implements Serializable {

//    private String id;
    private Map<String, Item> items = new HashMap<>();

    private OrderState state = OrderState.CART;

    private LocalDateTime paidAt;

    private LocalDateTime shippedAt;


//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
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

    public Order toOrder(){
        Order order = new Order();
        order.setItems(items);
        order.setPaidAt(paidAt);
        order.setShippedAt(shippedAt);
        order.setState(state);

        return order;
    }

    public OrderResource(Order order) {
//        this.id = order.getId();
        this.items = order.getItems();
        this.state = order.getState();
        this.paidAt = order.getPaidAt();
        this.shippedAt = order.getShippedAt();
    }

    public OrderResource(Map<String, Item> items, OrderState state, LocalDateTime paidAt, LocalDateTime shippedAt) {
        this.items = items;
        this.state = state;
        this.paidAt = paidAt;
        this.shippedAt = shippedAt;
    }

    public OrderResource() {
    }
}
