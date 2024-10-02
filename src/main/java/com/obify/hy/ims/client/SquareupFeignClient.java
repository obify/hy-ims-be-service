package com.obify.hy.ims.client;

import com.obify.hy.ims.client.model.*;
import com.obify.hy.ims.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "squareupFeignClient", url = "${squareup.url}", configuration = FeignClientConfiguration.class)
public interface SquareupFeignClient {

    @GetMapping("/locations")
    ResponseEntity<LocationModelWrapper> getAllLocations(@RequestHeader("Authorization") String token);

    @GetMapping("/locations/{locationId}")
    ResponseEntity<SingleLocationModelWrapper> getLocationDetail(@RequestHeader("Authorization") String token, @PathVariable String locationId);

    @PostMapping("/catalog/search-catalog-items")
    ResponseEntity<ProductModelWrapper> getAllProducts(@RequestHeader("Authorization") String token, @RequestBody ProductRequestModel productRequestModel);

    @GetMapping("/catalog/list?types=category")
    ResponseEntity<CategoryModelWrapper> getAllCategories(@RequestHeader("Authorization") String token);

    @PostMapping("/orders/search")
    ResponseEntity<SalesModelWrapper> getFilteredSales(@RequestHeader("Authorization") String token, @RequestBody SalesRequestModel model);
}
