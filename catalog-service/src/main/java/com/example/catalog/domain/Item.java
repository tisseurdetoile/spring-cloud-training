package com.example.catalog.domain;

public class Item {
	private String id;
	
	private String name;
	
	private String description;
	
	private double price;

	public Item() {}

	private Item(Builder builder) {
		this.name = builder.name;
		this.description = builder.description;
		this.price = builder.price;
	}
	
	public static Builder of(String name) {
		return new Builder(name);
	}
	
	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
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
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public static class Builder {
		private final String name;
		private String description = "";
		private double price = 0.0;
		
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
		
		public Item build() {
			return new Item(this);
		}
	}
}
