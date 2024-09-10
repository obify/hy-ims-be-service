package com.obify.hy.ims.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.obify.hy.ims.entity.*;
import com.obify.hy.ims.service.AuthService;
import com.obify.hy.ims.service.impl.MerchantServiceImpl;
import com.obify.hy.ims.util.CommonUtil;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.obify.hy.ims.dto.LoginRequestDTO;
import com.obify.hy.ims.dto.UserDTO;
import com.obify.hy.ims.dto.JwtResponse;
import com.obify.hy.ims.dto.MessageResponse;
import com.obify.hy.ims.repository.RoleRepository;
import com.obify.hy.ims.repository.UserRepository;
import com.obify.hy.ims.security.jwt.JwtUtils;
import com.obify.hy.ims.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private MerchantServiceImpl merchantService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(),
												 userDetails.getFirstName(),
				                                 userDetails.getLastName(),
												 userDetails.getEmail(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already taken!"));
		}
		// Create new user's account
		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "ROLE_MERCHANT":
					Role modRole = roleRepository.findByName(ERole.ROLE_MERCHANT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				case "ROLE_MANAGER":
					Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(managerRole);

					break;
				case "ROLE_VENDOR":
					Role vendorRole = roleRepository.findByName(ERole.ROLE_VENDOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(vendorRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/loggedIn")
	public ResponseEntity<?> loggedInUser(){
		UserDetailsImpl userDetails = commonUtil.loggedInUser();
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@PostMapping("/signup-merchant")
	public ResponseEntity<?> registerMerchant(@Valid @RequestBody UserDTO signUpRequest) {
		String userId = authService.registerUser(signUpRequest);
		return ResponseEntity.ok(new MessageResponse("Merchant registered successfully! with Id: "+userId));
	}

	@PostMapping("/signup-manager")
	@PreAuthorize("hasRole('MERCHANT')")
	public ResponseEntity<?> registerManager(@Valid @RequestBody UserDTO signUpRequest) {
		String userId = authService.registerUser(signUpRequest);
		UserDetailsImpl userDetails = commonUtil.loggedInUser();
		MerchantManager mm = new MerchantManager();
		mm.setManagerId(userId);
		mm.setMerchantId(userDetails.getId());
		mm = merchantService.saveManager(mm);
		return ResponseEntity.ok(new MessageResponse("Manager registered successfully with Id: "+mm.getId()));
	}

	@PostMapping("/signup-vendor")
	@PreAuthorize("hasRole('MERCHANT')")
	public ResponseEntity<?> registerVendor(@Valid @RequestBody UserDTO signUpRequest) {
		String userId = authService.registerUser(signUpRequest);
		UserDetailsImpl userDetails = commonUtil.loggedInUser();
		MerchantVendor mv = new MerchantVendor();
		mv.setVendorId(userId);
		mv.setMerchantId(userDetails.getId());
		mv = merchantService.saveVendor(mv);
		return ResponseEntity.ok(new MessageResponse("Vendor registered successfully with Id: "+mv.getId()));
	}

}
