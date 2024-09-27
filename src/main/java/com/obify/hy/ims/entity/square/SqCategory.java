package com.obify.hy.ims.entity.square;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sqcategory")
public class SqCategory {
    @Id
    private String id;
    private String name;
    private String merchantId;
}
