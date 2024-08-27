package com.obify.hy.ims.service;

import com.obify.hy.ims.entity.MerchantManager;
import com.obify.hy.ims.entity.MerchantVendor;

public interface MerchantService {

    MerchantManager saveManager(MerchantManager mg);
    MerchantVendor saveVendor(MerchantVendor mv);
}
