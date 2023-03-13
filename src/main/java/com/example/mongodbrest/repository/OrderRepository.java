package com.example.mongodbrest.repository;

import com.example.mongodbrest.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}