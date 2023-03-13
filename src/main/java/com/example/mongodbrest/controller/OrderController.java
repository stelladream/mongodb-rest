package com.example.mongodbrest.controller;

import com.example.mongodbrest.model.Order;
import com.example.mongodbrest.model.Product;
import com.example.mongodbrest.repository.OrderRepository;
import com.example.mongodbrest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {

        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            List<Product> products = productRepository.findAllByIdIn(order.getProductIds());
            order.setProducts(products);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            List<Product> products = new ArrayList<>();
            for (String productId : order.getProductIds()) {
                products.add(productRepository.findById(productId).orElse(null));
            }
            order.setProducts(products);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    // 새로운 Order 생성
    @PostMapping("")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {

        System.out.println(order);
        List<Product> products = order.getProducts();
        productRepository.saveAll(products);

        List<String> productIds = new ArrayList<>();
        for (Product product : products)
            productIds.add(product.getId());

        order.setProductIds(productIds);

        Order newOrder = orderRepository.save(order);

        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);

    }


    // 특정 Order 수정
    /*@PutMapping("/{id}/products")

    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Product product) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {

            productRepository.save(product);

            Order order = optionalOrder.get();
            order.getProductIds().add(product.getId());

            orderRepository.save(order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable String id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // 1. Order 내에 있는 Product ID 가져오기
            List<String> productIds = existingOrder.getProductIds();

            // 2. Product 삭제
            for (String productId : productIds) {
                productRepository.deleteById(productId);
            }

            // 3. Order 삭제
            orderRepository.delete(existingOrder);

            return new ResponseEntity<>(existingOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}