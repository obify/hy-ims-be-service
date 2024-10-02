package com.obify.hy.ims.entity.fi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "fiproductingredient")
public class FiProductIngredient {
    @Id
    private String id;
    private String productId;
    private String productName;
    private String merchantId;
    private List<FiIngredient> ingredients;
    private LocalDateTime createdAt;
}
