package kr.ac.hansung.linkedspaces;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    // 추가적인 메소드가 필요한 경우 여기에 작성합니다.
}