package com.obify.hy.ims.repository;

import com.obify.hy.ims.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByNameContaining(String name);
    List<Category> findAllByTypeContaining(String type);
}
