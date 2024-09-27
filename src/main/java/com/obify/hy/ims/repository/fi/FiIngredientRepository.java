package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.FiIngredient;
import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FiIngredientRepository extends MongoRepository<FiPlannedInventory, String> {
}
