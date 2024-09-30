package com.obify.hy.ims.entity.fi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "addinventory")
public class FiIngredient {
    @Id
    private String id;
    private String ingredientId;
    private String ingredient;
    private Float quantity;
    private Float remainingQty;
    private String unit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime salesToDtTime;
    private String merchantId;
}
