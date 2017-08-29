package com.example.orders.repository;

import com.example.orders.domain.Item;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends Repository<Item, String> {
    List<Item> findAll();
    Optional<Item> findOne(String id);

    Item save(Item item);

    void delete(Item item);
}
