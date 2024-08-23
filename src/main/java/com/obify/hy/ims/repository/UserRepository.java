package com.obify.hy.ims.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.obify.hy.ims.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String username);
  Boolean existsByEmail(String email);
}
