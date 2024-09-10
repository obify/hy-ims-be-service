package com.obify.hy.ims.repository;

import com.obify.hy.ims.entity.MerchantVendor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MerchantVendorRepository extends MongoRepository<MerchantVendor, String> {
    List<MerchantVendor> findAllByMerchantId(String merchantId);
}
