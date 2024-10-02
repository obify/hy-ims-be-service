package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.FiIngredient;
import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PlannedInventoryRepository extends MongoRepository<FiIngredient, String> {
    List<FiPlannedInventory> findAllByMerchantIdAndCreatedAtBetween(String merchantId, LocalDateTime d1, LocalDateTime d2);
    List<FiIngredient> findAllByMerchantId(String merchantId);
    FiIngredient findFirstByMerchantId(String merchantId);
    FiIngredient findByIngredientId(String ingredientId);
    @Query("SELECT p FROM FiIngredient p WHERE p.ingredientId = :ingredientId ORDER BY p.createdAt DESC LIMIT 1")
    FiIngredient findFirstByIngredientIdAndCreatedAt(@Param("ingredientId") String ingredientId);
}
