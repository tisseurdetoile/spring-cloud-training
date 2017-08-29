package com.example.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.catalog.domain.Item;

public interface ItemRepository extends Repository<Item, String> {
	List<Item> findAll();
	Optional<Item> findOne(String id);
	
	Item save(Item item);
	
	void delete(Item item);
}
