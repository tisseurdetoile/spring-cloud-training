package com.example.orders.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    
    private ItemResource toResource(Item item) {
    	ItemResource resource = new ItemResource(item);
    	// TODO add links
    	
    	return resource;
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

    @PostMapping("/{id}/items")
    public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody ItemResource request) {
        return withOrder(id, order -> {
            order.addItem(request.toItem());
            order = orderRepository.save(order);
            
            return ResponseEntity.ok(toResource(order));
        });
    }
    
    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<?> editQuantity(@PathVariable String id, @PathVariable String itemId, @Valid @RequestBody ItemResource request) {
    	return withOrderItem(id, itemId, (order, item) -> {
    		item.setQuantity(request.getQuantity());
    		
    		orderRepository.save(order);
    		
    		return ResponseEntity.ok(toResource(item));
    	});
    }

    private ResponseEntity<?> withOrderItem(String id, String itemId, BiFunction<Order, Item, ResponseEntity<?>> mapper) {
		return orderRepository.findOne(id)
				.flatMap(order -> order.getItem(itemId)
						.map(item -> mapper.apply(order, item)))
				.orElse(ResponseEntity.notFound().build());
	}

	private ResponseEntity<?> withOrder(String id, Function<Order, ResponseEntity<?>> mapper) {
        return orderRepository.findOne(id)
                .map(mapper)
                .orElse(ResponseEntity.notFound().build());
    }

}
