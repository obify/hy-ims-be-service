package com.obify.hy.ims.request;


import com.obify.hy.ims.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryServiceRequest {
    private CategoryDTO categoryDTO;
}
