package com.obify.hy.ims.entity.square;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "remaininginventory")
public class RemainingInventory {

    @Id
    private String id;
    private String ingredientId;
    private String ingredient;
    private Float usedQty;
    private Float remainingQty;
    private String unit;
    private String merchantId;
}
