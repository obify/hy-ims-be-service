package com.obify.hy.ims.repository;

import com.obify.hy.ims.dto.CategoryDTO;
import com.obify.hy.ims.entity.Category;
import com.obify.hy.ims.entity.MerchantManager;
import com.obify.hy.ims.request.CategoryServiceRequest;
import com.obify.hy.ims.response.CategoryServiceResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryServiceRepository extends MongoRepository<Category, String> {

}
