package com.example.mongodbrest.repository;

import com.example.mongodbrest.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByIdIn(List<String> productIds);

}