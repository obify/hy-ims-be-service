package com.obify.hy.ims.service;

import com.obify.hy.ims.request.CategoryServiceRequest;
import com.obify.hy.ims.response.CategoryServiceResponse;

public interface CategoryService {
    CategoryServiceResponse categoryRegistration(CategoryServiceRequest categoryServiceRequest);
}
