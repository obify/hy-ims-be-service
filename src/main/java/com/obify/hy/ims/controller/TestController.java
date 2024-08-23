package com.obify.hy.ims.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/manager")
	@PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('ADMIN')")
	public String userAccess() {
		return "MERCHANT/MANAGER/ADMIN Content.";
	}

	@GetMapping("/merchant")
	@PreAuthorize("hasRole('MERCHANT')")
	public String moderatorAccess() {
		return "MERCHANT Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
