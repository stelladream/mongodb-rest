package com.example.mongodbrest;

import com.example.mongodbrest.model.Order;
import com.example.mongodbrest.model.Product;
import com.example.mongodbrest.repository.OrderRepository;
import com.example.mongodbrest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderLoader implements ApplicationRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args)    {

        Order order = new Order();

        Order.Address address = new Order.Address();
        address.setStreet("123 Main St.");
        address.setCity("New York");
        address.setZipcode("10001");
        order.setAddress(address);

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setDescription("This is product " + i + ".");
            product.setPrice(i * 10.0);
            product.setCreatedAt(new Date());
            products.add(product);
        }

        productRepository.saveAll(products);

        List<String> productIds = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            productIds.add(products.get(i).getId());

        order.setProductIds(productIds);
        order.setCustomerName("computer");

        orderRepository.save(order);
    }
}
