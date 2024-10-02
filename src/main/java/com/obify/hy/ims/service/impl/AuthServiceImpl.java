package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.ErrorDTO;
import com.obify.hy.ims.dto.UserDTO;
import com.obify.hy.ims.entity.ERole;
import com.obify.hy.ims.entity.Role;
import com.obify.hy.ims.entity.User;
import com.obify.hy.ims.exception.BusinessException;
import com.obify.hy.ims.repository.RoleRepository;
import com.obify.hy.ims.repository.UserRepository;
import com.obify.hy.ims.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public String registerUser(UserDTO signUpRequest){
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            List<ErrorDTO> errors = List.of(new ErrorDTO("AUTH_001", "Email is already taken"));
            throw new BusinessException(errors);
        }
        // Create new user's account
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setPos(signUpRequest.getPos());
        user.setSquareToken(signUpRequest.getSquareToken());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setActive(false);
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

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
        user.setRoles(roles);
        user = userRepository.save(user);

        return user.getId();
    }
}
