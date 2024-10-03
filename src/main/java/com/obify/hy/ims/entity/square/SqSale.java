package com.obify.hy.ims.entity.square;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sqsale")
public class SqSale {

    @Id
    private String id;
    private String productName;
    private String imageUrl;
    private Integer productCountSold;
    private LocalDateTime updatedAt;
    private String merchantId;
}
