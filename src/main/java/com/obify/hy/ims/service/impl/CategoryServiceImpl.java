package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.CategoryDTO;
import com.obify.hy.ims.entity.Category;
import com.obify.hy.ims.repository.CategoryServiceRepository;
import com.obify.hy.ims.request.CategoryServiceRequest;
import com.obify.hy.ims.response.CategoryServiceResponse;
import com.obify.hy.ims.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryServiceRepository categoryServiceRepository;

    @Override
    public CategoryServiceResponse categoryRegistration(CategoryServiceRequest categoryServiceRequest) {
        CategoryServiceResponse categoryServiceResponse = new CategoryServiceResponse();
        Category category = new Category();
        if(categoryServiceRequest.getCategoryDTO() != null) {
            CategoryDTO categoryDTO = categoryServiceRequest.getCategoryDTO();
            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setType(categoryDTO.getType());
            category.setDescription(categoryDTO.getDescription());
        }
        Category categoryResponse = categoryServiceRepository.save(category);
        if (categoryResponse != null) {
            categoryServiceResponse.setCategory(categoryResponse);
        }
        return categoryServiceResponse;
    }
}
