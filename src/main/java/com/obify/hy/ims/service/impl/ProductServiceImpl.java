package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.ProductDTO;
import com.obify.hy.ims.service.ImsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ImsService<ProductDTO, ProductDTO> {

    @Override
    public ProductDTO add(ProductDTO input) {
        return null;
    }

    @Override
    public ProductDTO update(ProductDTO input, Long id) {
        return null;
    }

    @Override
    public ProductDTO delete(Long id) {
        return null;
    }

    @Override
    public ProductDTO get(Long id) {
        return null;
    }

    @Override
    public List<ProductDTO> getAll() {
        return List.of();
    }

    @Override
    public List<ProductDTO> search(ProductDTO input) {
        return List.of();
    }
}
