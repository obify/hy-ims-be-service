package com.obify.hy.ims.entity.square;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sqsale")
public class SqSale {

    @Id
    private String id;
    private String productName;
    private Integer productCountSold;
    private String merchantId;
}
