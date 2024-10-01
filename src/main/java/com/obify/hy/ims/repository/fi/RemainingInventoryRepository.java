package com.obify.hy.ims.repository.fi;

import com.obify.hy.ims.entity.square.RemainingInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RemainingInventoryRepository extends MongoRepository<RemainingInventory, String> {
}
