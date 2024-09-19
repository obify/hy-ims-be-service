package com.obify.hy.ims.client;

import com.obify.hy.ims.client.model.LocationModelWrapper;
import com.obify.hy.ims.client.model.SalesModelWrapper;
import com.obify.hy.ims.client.model.SalesRequestModel;
import com.obify.hy.ims.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "squareupFeignClient", url = "${squareup.url}", configuration = FeignClientConfiguration.class)
public interface SquareupFeignClient {

    @GetMapping("/locations")
    ResponseEntity<LocationModelWrapper> getAllLocations();
    @PostMapping("/orders/search")
    ResponseEntity<SalesModelWrapper> getFilteredSales(@RequestBody SalesRequestModel model);
}
