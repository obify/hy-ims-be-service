package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.MessageResponse;
import com.obify.hy.ims.request.CategoryServiceRequest;
import com.obify.hy.ims.response.CategoryServiceResponse;
import com.obify.hy.ims.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryServiceResponse> categoryRegistration
            (@Valid @RequestBody CategoryServiceRequest categoryServiceRequest) {
        return new ResponseEntity<>(categoryService.categoryRegistration
                (categoryServiceRequest), HttpStatus.CREATED);
    }

}
