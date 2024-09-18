package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.CategoryDTO;
import com.obify.hy.ims.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping("/categories")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> add
            (@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.add(categoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> update
            (@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable String id) {
        return new ResponseEntity<>(categoryService.update(categoryDTO, id), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable String id) {
        return new ResponseEntity<>(categoryService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return new ResponseEntity<>(categoryService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/categories/search")
    public ResponseEntity<List<CategoryDTO>> search(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.search(categoryDTO), HttpStatus.OK);
    }

}
