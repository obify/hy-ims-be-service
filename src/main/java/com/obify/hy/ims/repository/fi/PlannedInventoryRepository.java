package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlannedInventoryRepository extends MongoRepository<FiPlannedInventory, String> {
    List<FiPlannedInventory> findAllByMerchantIdAndStartDateTimeBetween(String merchantId, LocalDateTime d1, LocalDateTime d2);
    List<FiPlannedInventory> findAllByMerchantId(String merchantId);
}
