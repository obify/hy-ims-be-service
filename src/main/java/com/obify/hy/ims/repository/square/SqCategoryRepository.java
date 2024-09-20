package com.obify.hy.ims.repository.square;

import com.obify.hy.ims.entity.square.SqCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SqCategoryRepository extends MongoRepository<SqCategory, String> {
    Optional<SqCategory> findAllByNameContaining(String name);
}
