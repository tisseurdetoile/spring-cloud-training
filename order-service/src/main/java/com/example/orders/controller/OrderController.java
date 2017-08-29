package com.example.orders.controller;

import com.example.orders.domain.Order;
import com.example.orders.repository.OrderRepository;
import com.example.orders.resource.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrderResource request) {
        Order order = orderRepository.save(request.toOrder());

        OrderResource resource = toResource(order);

        return ResponseEntity.created(URI.create(resource.getId().getHref()))
                .body(resource);
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody OrderResource request) {
        return withOrder(id, order -> {
            order.setItems(request.getItems());
            order.setState(request.getState());
            order.setPaidAt(request.getPaidAt());
            order.setShippedAt(request.getShippedAt());

            order = orderRepository.save(order);
            return ResponseEntity.ok(toResource(order));
        });
    }

    private ResponseEntity<?> withOrder(String id, Function<Order, ResponseEntity<?>> mapper) {
        return orderRepository.findOne(id)
                .map(mapper)
                .orElse(ResponseEntity.notFound().build());
    }


}
