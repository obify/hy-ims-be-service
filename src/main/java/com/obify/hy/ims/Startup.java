package com.obify.hy.ims;

import com.obify.hy.ims.entity.ERole;
import com.obify.hy.ims.entity.Role;
import com.obify.hy.ims.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Startup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<Role> optRoleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
        if(optRoleAdmin.isEmpty()){
            Role role = new Role();
            role.setName(ERole.ROLE_ADMIN);
            roleRepository.save(role);
        }

        Optional<Role> optRoleOwn = roleRepository.findByName(ERole.ROLE_MANAGER);
        if(optRoleOwn.isEmpty()){
            Role role = new Role();
            role.setName(ERole.ROLE_MANAGER);
            roleRepository.save(role);
        }

        Optional<Role> optRoleMe = roleRepository.findByName(ERole.ROLE_MERCHANT);
        if(optRoleMe.isEmpty()){
            Role role = new Role();
            role.setName(ERole.ROLE_MERCHANT);
            roleRepository.save(role);
        }

        Optional<Role> optRoleVe = roleRepository.findByName(ERole.ROLE_VENDOR);
        if(optRoleVe.isEmpty()){
            Role role = new Role();
            role.setName(ERole.ROLE_VENDOR);
            roleRepository.save(role);
        }
    }
}
