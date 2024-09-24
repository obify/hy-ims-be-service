package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.FiProductIngredient;
import com.obify.hy.ims.entity.fi.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductIngredientRepository extends MongoRepository<FiProductIngredient, String> {
}
