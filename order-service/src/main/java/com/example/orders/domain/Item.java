package com.example.orders.domain;

public class Item {
	private String name;
	private String description;
	private int quantity;
	private double price;

	private Item(Builder builder) {
		this.name = builder.name;
		this.description = builder.description;
		this.price = builder.price;
		this.quantity = builder.quantity;
	}

	public Item() { }

	public static Builder of(String name) {
		return new Builder(name);
	}


	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public int addQuantity(int other) {
		quantity += other;
		return quantity;
	}

	public static class Builder {
		private final String name;
		private String description = "";
		private double price = 0.0;
		private int quantity = 0;

		private Builder(String name) {
			this.name = name;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder price(double price) {
			this.price = price;
			return this;
		}

		public Builder quantity(int quantity){
			this.quantity = quantity;
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}

}
