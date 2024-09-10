package com.obify.hy.ims.repository;

import com.obify.hy.ims.entity.MerchantManager;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MerchantManagerRepository extends MongoRepository<MerchantManager, String> {
    List<MerchantManager> findAllByMerchantId(String merchantId);
}
