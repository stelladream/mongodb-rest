package kr.ac.hansung.linkedspaces;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    // 모든 Product 조회
    @GetMapping("")
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // 새로운 Product 생성
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = repository.save(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    // 특정 Product 조회
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            return new ResponseEntity<Product>(optionalProduct.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 특정 Product 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product productUpdates) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            if (productUpdates.getName() != null) {
                existingProduct.setName(productUpdates.getName());
            }

            if (productUpdates.getDescription() != null) {
                existingProduct.setDescription(productUpdates.getDescription());
            }

            if (productUpdates.getPrice() != 0) {
                existingProduct.setPrice(productUpdates.getPrice());
            }

            repository.save(existingProduct);

            return new ResponseEntity<Product>(existingProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 특정 Product 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            repository.delete(existingProduct);
            return new ResponseEntity<Product>(existingProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
