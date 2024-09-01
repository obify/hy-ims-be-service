package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.CategoryDTO;
import com.obify.hy.ims.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDTO> categoryRegistration
            (@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.add(categoryDTO), HttpStatus.CREATED);
    }

}
