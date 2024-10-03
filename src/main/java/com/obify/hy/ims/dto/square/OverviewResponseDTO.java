package com.obify.hy.ims.dto.square;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.obify.hy.ims.entity.fi.FiIngredient;
import com.obify.hy.ims.entity.square.RemainingInventory;
import com.obify.hy.ims.entity.square.SqSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OverviewResponseDTO {
    private List<RemainingInventory> remainingInventories;
    private String fromDateTime;
    private String toDateTime;
    private List<SqSale> sales;
}
