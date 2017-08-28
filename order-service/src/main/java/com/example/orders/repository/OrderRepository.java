package com.example.orders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.orders.domain.Order;

public interface OrderRepository extends Repository<Order, String> {
	Optional<Order> find(String id);
	List<Order> findAll();

	Order save(Order order);
	
	void delete(Order order);
}
