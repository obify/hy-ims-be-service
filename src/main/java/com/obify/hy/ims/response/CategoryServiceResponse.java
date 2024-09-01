package com.obify.hy.ims.response;

import com.obify.hy.ims.dto.CategoryDTO;
import com.obify.hy.ims.dto.ErrorDTO;
import com.obify.hy.ims.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryServiceResponse {

    private Category category;
    private ErrorDTO errorDTO;
}
