package com.obify.hy.ims.controller;

import com.obify.hy.ims.dto.UserDTO;
import com.obify.hy.ims.security.services.UserDetailsImpl;
import com.obify.hy.ims.service.MerchantService;
import com.obify.hy.ims.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class MerchantController {

    @Autowired
    MerchantService merchantService;
    @Autowired
    CommonUtil commonUtil;

    @GetMapping("/merchants/managers")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<List<UserDTO>> getAllManagers(){
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        return new ResponseEntity<>(merchantService.getAllManagersForMerchant(userDetails.getId()), HttpStatus.OK);
    }

    @GetMapping("/merchants/vendors")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<List<UserDTO>> getAllVendors(){
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        return new ResponseEntity<>(merchantService.getAllVendorsForMerchant(userDetails.getId()), HttpStatus.OK);
    }
}
