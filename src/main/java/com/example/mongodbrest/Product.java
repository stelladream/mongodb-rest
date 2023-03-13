package kr.ac.hansung.linkedspaces;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private Date createdAt;

    // 생성자, getter/setter, toString 생략
}
