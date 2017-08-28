package com.example.orders.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class Order {
	private String id;
	private Map<String, Item> items = new HashMap<>();
	
	private OrderState state = OrderState.CART;
	
	private LocalDateTime paidAt;
	
	private LocalDateTime shippedAt;
	
	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}
	
	public Collection<Item> getItems() {
		return items.values();
	}
	
	public void addItem(Item item) {
		String id = item.getId();
		
		if (items.containsKey(id)) {
			items.get(id).addQuantity(item.getQuantity());
		} else {
			items.put(id, item);
		}
	}
	
	public LocalDateTime getPaidAt() {
		return paidAt;
	}
	
	public LocalDateTime getShippedAt() {
		return shippedAt;
	}
	
	public void pay() {
		Assert.state(canPay(), "This order has already been paid");
		
		state = OrderState.PAID;
		paidAt = LocalDateTime.now();
	}

	public boolean canPay() {
		return state == OrderState.CART;
	}
	
	public void ship() {
		Assert.state(canShip(), "This order cannot be shipped");
		
		state = OrderState.SHIPPED;
		shippedAt = LocalDateTime.now();
	}

	public boolean canShip() {
		return state == OrderState.SHIPPED;
	}
}
