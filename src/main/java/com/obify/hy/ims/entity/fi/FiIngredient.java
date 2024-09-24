package com.obify.hy.ims.entity.fi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiIngredient {
    private String ingredient;
    private Integer quantity;
    private String unit;
}
