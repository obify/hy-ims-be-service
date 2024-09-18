package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.LocationDTO;
import com.obify.hy.ims.service.impl.LocationServiceImpl;
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
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService;

    @PostMapping("/locations")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<LocationDTO> add
            (@Valid @RequestBody LocationDTO locationDTO) {
        return new ResponseEntity<>(locationService.add(locationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/locations/{id}")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<LocationDTO> update
            (@Valid @RequestBody LocationDTO locationDTO,@PathVariable String id) {
        return new ResponseEntity<>(locationService.update(locationDTO, id), HttpStatus.OK);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getAll() {
        return new ResponseEntity<>(locationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<LocationDTO> get(@PathVariable String id) {
        return new ResponseEntity<>(locationService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/locations/{id}")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return new ResponseEntity<>(locationService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/locations/search")
    public ResponseEntity<List<LocationDTO>> search(@RequestBody LocationDTO locationDTO) {
        return new ResponseEntity<>(locationService.search(locationDTO), HttpStatus.OK);
    }

}
