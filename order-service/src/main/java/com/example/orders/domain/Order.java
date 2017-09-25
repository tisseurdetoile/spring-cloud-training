package com.example.orders.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;

public class Order {
	private String id;
	private Map<String, Item> items = new HashMap<>();

	private OrderState state = OrderState.CART;

	private LocalDateTime paidAt;

	private LocalDateTime shippedAt;

	public Order() {}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public Collection<Item> getItems() {
		return Collections.unmodifiableCollection(items.values());
	}

	public boolean hasItem(String name) {
		return items.containsKey(name);
	}
	
	public Item addItem(Item item) {
		String name = item.getName();

		if (items.containsKey(name)) {
			Item result = items.get(name);
			result.addQuantity(item.getQuantity());
			return result;

		} else {
			items.put(name, item);
			return item;
		}
	}

	public void removeItem(Item item) {
		String name = item.getName();

		if(items.containsKey(name)) {
			items.remove(name);
		}

	}
	
	public LocalDateTime getPaidAt() {
		return paidAt;
	}
	
	public LocalDateTime getShippedAt() {
		return shippedAt;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	public void setShippedAt(LocalDateTime shippedAt) {
		this.shippedAt = shippedAt;
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

	public Optional<Item> getItem(String itemName) {
		return Optional.ofNullable(items.get(itemName));
	}

}
