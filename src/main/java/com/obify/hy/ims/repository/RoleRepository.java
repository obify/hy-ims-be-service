package com.obify.hy.ims.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.obify.hy.ims.entity.ERole;
import com.obify.hy.ims.entity.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
