package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.FiProductIngredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductIngredientRepository extends MongoRepository<FiProductIngredient, String> {
   Optional<FiProductIngredient> findByProductName(String productName);
}
