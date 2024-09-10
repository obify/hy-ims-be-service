package com.obify.hy.ims.repository;

import com.obify.hy.ims.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {
    List<Location> findAllByNameContaining(String name);
}
