package com.example.orders.resource;

import com.example.orders.domain.Item;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "item", collectionRelation = "items")
public class ItemResource extends ResourceSupport {
    private String name;

    private String description;

    private int quantity;

    private double price;

    public ItemResource() {
    }

    public ItemResource(Item item){
        this.name = item.getName();
        this.description = item.getDescription();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
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

    public Item toItem() {
        return Item.of(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .build();
    }

}
