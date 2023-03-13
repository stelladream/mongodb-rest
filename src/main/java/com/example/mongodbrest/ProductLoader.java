package kr.ac.hansung.linkedspaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader implements ApplicationRunner {
    @Autowired
    private ProductRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception   {
        // 10개의 샘플 데이터 생성
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setDescription("This is product " + i + ".");
            product.setPrice(i * 10.0);
            product.setCreatedAt(new Date());
            products.add(product);
        }

        // MongoDB에 저장
        repository.saveAll(products);
    }
}
