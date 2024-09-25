package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlannedInventoryRepository extends MongoRepository<FiPlannedInventory, String> {
}
