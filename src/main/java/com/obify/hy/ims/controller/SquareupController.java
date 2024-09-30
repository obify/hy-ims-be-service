package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.RequestLocationDTO;
import com.obify.hy.ims.entity.square.SqCategory;
import com.obify.hy.ims.entity.square.SqProduct;
import com.obify.hy.ims.entity.square.SqSale;
import com.obify.hy.ims.repository.square.SqSalesRepository;
import com.obify.hy.ims.service.SquareupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class SquareupController {

    @Autowired
    private SquareupService squareupService;
    @Autowired
    private SqSalesRepository sqSalesRepository;

    @PostMapping("/sq/refresh/categories")
    //@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<String> refreshCategories(@RequestBody RequestLocationDTO locationDTO) {
        String msg = squareupService.processCategoryData(locationDTO.getToken(), locationDTO.getMerchantId());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/sq/refresh/products")
    //@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<String> refreshProducts(@RequestBody RequestLocationDTO locationDTO) {
        String msg = squareupService.processProductData(locationDTO.getToken(), locationDTO.getMerchantId());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/sq/refresh/sales")
    //@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<String> refreshSales(@RequestBody OverviewRequestDTO requestDTO) {
        sqSalesRepository.deleteAllByMerchantId(requestDTO.getMerchantId());
        String msg = squareupService.processSalesData(requestDTO);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/sq/fetch/sales")
    //@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<List<SqSale>> fetchSales() {
        List<SqSale> sales = squareupService.getAllSales();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }
    @GetMapping("/sq/fetch/categories")
    public ResponseEntity<List<SqCategory>> fetchCategories() {
        List<SqCategory> categories = squareupService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("/sq/fetch/products")
    public ResponseEntity<List<SqProduct>> fetchProducts() {
        List<SqProduct> products = squareupService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
