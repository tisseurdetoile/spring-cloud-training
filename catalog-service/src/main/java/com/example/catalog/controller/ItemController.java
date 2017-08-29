package com.example.catalog.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.catalog.domain.Item;
import com.example.catalog.repository.ItemRepository;
import com.example.catalog.resource.ItemResource;

@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemRepository itemRepository;
	
	private ItemResource toResource(Item item) {
		ItemResource resource = new ItemResource(item);
		
		resource.add(linkTo(methodOn(ItemController.class).findOne(item.getId())).withSelfRel());
		
		return resource;
	}
	
	private Resources<ItemResource> toResources(List<Item> items) {
		Resources<ItemResource> resources = new Resources<>(
				items.stream()
				.map(this::toResource)
				.collect(Collectors.toList()));
		
		resources.add(linkTo(methodOn(ItemController.class).findAll()).withSelfRel());
		return resources;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ItemResource request) {
		Item item = itemRepository.save(request.toItem());
		
		ItemResource resource = toResource(item);
		
		return ResponseEntity.created(URI.create(resource.getId().getHref()))
				.body(resource);
	}
	
	@GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		List<Item> items = itemRepository.findAll();
		
		return ResponseEntity.ok(toResources(items));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOne(@PathVariable String id) {
		return withItem(id, item -> {
			return ResponseEntity.ok(toResource(item));
		});
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody ItemResource request) {
		return withItem(id, item -> {
			item.setDescription(request.getDescription());
			item.setPrice(request.getPrice());
			
			item = itemRepository.save(item);
			return ResponseEntity.ok(toResource(item));
		});
	}
	
	private ResponseEntity<?> withItem(String id, Function<Item, ResponseEntity<?>> mapper) {
		return itemRepository.findOne(id)
				.map(mapper)
				.orElse(ResponseEntity.notFound().build());
	}
}
