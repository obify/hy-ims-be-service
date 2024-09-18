package com.obify.hy.ims.client;

import com.obify.hy.ims.client.model.LocationModelWrapper;
import com.obify.hy.ims.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "squareupFeignClient", url = "${squareup.url}", configuration = FeignClientConfiguration.class)
public interface SquareupFeignClient {

    @GetMapping("/locations")
    ResponseEntity<LocationModelWrapper> getAllLocations();
}
