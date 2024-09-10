package com.obify.hy.ims.repository;


import com.obify.hy.ims.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByNameContaining(String name);
}
