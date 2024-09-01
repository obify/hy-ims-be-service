package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.CategoryDTO;
import com.obify.hy.ims.dto.ErrorDTO;
import com.obify.hy.ims.entity.Category;
import com.obify.hy.ims.exception.BusinessException;
import com.obify.hy.ims.repository.CategoryRepository;
import com.obify.hy.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ImsService<CategoryDTO, CategoryDTO> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryDTO add(CategoryDTO input) {
        Category category = new Category();
        BeanUtils.copyProperties(input, category);
        category = categoryRepository.save(category);
        BeanUtils.copyProperties(category, input);
        return input;
    }

    @Override
    public CategoryDTO update(CategoryDTO input, String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->{
                    List<ErrorDTO> errors =
                            List.of(new ErrorDTO("CAT_001", "Error occurred while saving new category"));
                    return new BusinessException(errors);
                });
        BeanUtils.copyProperties(input, category);
        category = categoryRepository.save(category);
        BeanUtils.copyProperties(category, input);
        return input;
    }

    @Override
    public String delete(String id) {
        categoryRepository.deleteById(id);
        return "Deleted successfully with Id: "+id;
    }

    @Override
    public CategoryDTO get(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->{
                    List<ErrorDTO> errors =
                            List.of(new ErrorDTO("CAT_003", "Error occurred while while finding the element"));
                    return new BusinessException(errors);
                });
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        CategoryDTO categoryDTO =  null;
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category category: categories){
            categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);;
            dtos.add(categoryDTO);
        }
        return dtos;
    }

    @Override
    public List<CategoryDTO> search(CategoryDTO input) {

        List<Category> categories = null;
        if(input.getName() != null){
            categories = categoryRepository.findAllByNameContaining(input.getName());
        } else if (input.getType() != null) {
            categories = categoryRepository.findAllByTypeContaining(input.getType());
        }
        CategoryDTO categoryDTO =  null;
        List<CategoryDTO> dtos = new ArrayList<>();
        for(Category category: categories){
            categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);;
            dtos.add(categoryDTO);
        }
        return dtos;
    }
}
