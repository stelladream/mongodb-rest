package com.example.mongodbrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String customerName;

    /*@DBRef
    private List<Product> products;*/

    //private List<ObjectId> products;

    @Field("products")
    @JsonIgnore
    private List<String> productIds;

    @Transient
    private List<Product> products;

    private Address address;

    @Data
    public static class Address {

        private String street;
        private String city;
        private String zipcode;

    }
}