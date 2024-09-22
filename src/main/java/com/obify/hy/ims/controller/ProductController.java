package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.ProductDTO;
import com.obify.hy.ims.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/products")
    @PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> add
            (@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.add(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> update
            (@Valid @RequestBody ProductDTO productDTO,@PathVariable String id) {
        return new ResponseEntity<>(productService.update(productDTO, id), HttpStatus.OK);
    }

    @GetMapping("/products")
    //@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    //@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> get(@PathVariable String id) {
        return new ResponseEntity<>(productService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/products/search")
    @PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> search(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.search(productDTO), HttpStatus.OK);
    }
}
