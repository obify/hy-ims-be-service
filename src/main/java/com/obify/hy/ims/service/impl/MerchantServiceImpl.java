package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.entity.MerchantManager;
import com.obify.hy.ims.entity.MerchantVendor;
import com.obify.hy.ims.repository.MerchantManagerRepository;
import com.obify.hy.ims.repository.MerchantVendorRepository;
import com.obify.hy.ims.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantManagerRepository merchantManagerRepository;
    @Autowired
    private MerchantVendorRepository merchantVendorRepository;

    @Override
    public MerchantManager saveManager(MerchantManager mg) {
        return merchantManagerRepository.save(mg);
    }

    @Override
    public MerchantVendor saveVendor(MerchantVendor mv) {
        return merchantVendorRepository.save(mv);
    }
}
