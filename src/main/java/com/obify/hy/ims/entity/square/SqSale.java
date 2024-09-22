package com.obify.hy.ims.entity.square;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sqsale")
public class SqSale {

    private String productName;
    private Integer productCountSold;
}
