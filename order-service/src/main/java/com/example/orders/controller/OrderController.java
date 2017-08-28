package com.example.orders.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.orders.domain.Item;
import com.example.orders.domain.Order;
import com.example.orders.repository.OrderRepository;
import com.example.orders.resource.ItemResource;
import com.example.orders.resource.OrderResource;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    private OrderResource toResource(Order order) {
        OrderResource resource = new OrderResource(order);

        resource.add(linkTo(methodOn(OrderController.class).findOne(order.getId())).withSelfRel());
        resource.add(linkTo(methodOn(OrderController.class).getItems(order.getId())).withRel("items"));
        return resource;
    }

    private Resources<OrderResource> toResources(List<Order> orders) {
        Resources<OrderResource> resources = new Resources<>(
                orders.stream()
                        .map(this::toResource)
                        .collect(Collectors.toList()));

        resources.add(linkTo(methodOn(OrderController.class).findAll()).withSelfRel());
        return resources;
    }

    private ItemResource toResource(Order order, Item item) {
    	ItemResource resource = new ItemResource(item);

        resource.add(linkTo(methodOn(OrderController.class).getItem(order.getId(), item.getName())).withSelfRel());

        resource.add(linkTo(methodOn(OrderController.class).findOne(order.getId())).withRel("order"));
    	return resource;
    }

    private Resources<ItemResource> toResources(Order order, Collection<Item> items) {
        Resources<ItemResource> resources = new Resources<ItemResource>(
                items.stream()
                        .map(item -> this.toResource(order, item))
                        .collect(Collectors.toList()));

        resources.add(linkTo(methodOn(OrderController.class).getItems(order.getId())).withSelfRel());
        resources.add(linkTo(methodOn(OrderController.class).findOne(order.getId())).withRel("order"));

        return resources;
    }

    @PostMapping
    public ResponseEntity<?> create() {
        Order order = orderRepository.save(new Order());

        OrderResource resource = toResource(order);

        return ResponseEntity.created(URI.create(resource.getId().getHref()))
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Order> orders = orderRepository.findAll();

        return ResponseEntity.ok(toResources(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        return withOrder(id, order -> {
            return ResponseEntity.ok(toResource(order));
        });
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id, @Valid @RequestBody ItemResource request) {
        return withOrder(id, order -> {
            order.removeItem(request.toItem());
            order = orderRepository.save(order);

            return ResponseEntity.noContent().build();
        });
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> edit(@PathVariable String id) {
    	return withOrder(id, order -> {
    		if (!order.canPay()) {
    			return ResponseEntity.notFound().build();
    		}
    		
    		order.pay();
    		
    		return ResponseEntity.ok().build();
    	});
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<?> getItems(@PathVariable String id) {
        Order order =  orderRepository.findOne(id).get();
        return ResponseEntity.ok(toResources(order, order.getItems()));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<?> addItem(@PathVariable String id, @Valid @RequestBody ItemResource request) {
        return withOrder(id, order -> {
            boolean isNewItem = order.hasItem(request.getName());
            Item item = order.addItem(request.toItem());
            order = orderRepository.save(order);
            ItemResource itemResource = toResource(order, item);

            if(isNewItem){
                return ResponseEntity.created(URI.create(itemResource.getId().getHref()))
                        .body(itemResource);
            }

            return ResponseEntity.ok(toResource(order, item));

        });
    }

    @GetMapping("/{id}/items/{itemName}")
    public ResponseEntity<?> getItem(@PathVariable String id, @PathVariable String itemName) {
        return withOrderItem(id, itemName, (order, item) -> {

            return ResponseEntity.ok(toResource(order, item));
        });
    }

    @PutMapping("/{id}/items/{itemName}")
    public ResponseEntity<?> editQuantity(@PathVariable String id, @PathVariable String itemName, @Valid @RequestBody ItemResource request) {
    	return withOrderItem(id, itemName, (order, item) -> {
    		item.setQuantity(request.getQuantity());
    		
    		orderRepository.save(order);
    		
    		return ResponseEntity.ok(toResource(order));
    	});
    }

    @DeleteMapping("/{id}/items/{itemName}")
    public ResponseEntity<?> removeItem(@PathVariable String id, @PathVariable String itemName, @Valid @RequestBody ItemResource request) {
        return withOrderItem(id, itemName, (order, item) -> {
            order.removeItem(item);
            orderRepository.save(order);

            return ResponseEntity.noContent().build();
        });
    }

    private ResponseEntity<?> withOrderItem(String id, String itemName, BiFunction<Order, Item, ResponseEntity<?>> mapper) {
		return orderRepository.findOne(id)
				.flatMap(order -> order.getItem(itemName)
						.map(item -> mapper.apply(order, item)))
				.orElse(ResponseEntity.notFound().build());
	}

	private ResponseEntity<?> withOrder(String id, Function<Order, ResponseEntity<?>> mapper) {
        return orderRepository.findOne(id)
                .map(mapper)
                .orElse(ResponseEntity.notFound().build());
    }	
}
