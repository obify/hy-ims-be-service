package com.obify.hy.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "merchantvendors")
public class MerchantVendor {

    @Id
    private String id;
    private String merchantId;
    private String vendorId;
}
