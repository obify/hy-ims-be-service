package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    List<Ingredient> findAllByMerchantId(String merchantId);
}
