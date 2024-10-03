package com.obify.hy.ims.repository.square;

import com.obify.hy.ims.entity.square.SqProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SqProductRepository extends MongoRepository<SqProduct, String> {
    Optional<SqProduct> findFirstByName(String name);
    void deleteAllByMerchantId(String merchantId);
}
