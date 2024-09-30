package com.obify.hy.ims.repository.square;

import com.obify.hy.ims.entity.square.SqSale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SqSalesRepository extends MongoRepository<SqSale, String> {
    List<SqSale> findAllByMerchantId(String merchantId);
    List<SqSale> deleteAllByMerchantId(String merchantId);
    List<SqSale> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);
}
