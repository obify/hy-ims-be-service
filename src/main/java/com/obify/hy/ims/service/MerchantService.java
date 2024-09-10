package com.obify.hy.ims.service;

import com.obify.hy.ims.dto.UserDTO;
import com.obify.hy.ims.entity.MerchantManager;
import com.obify.hy.ims.entity.MerchantVendor;
import com.obify.hy.ims.entity.User;

import java.util.List;

public interface MerchantService {

    MerchantManager saveManager(MerchantManager mg);
    MerchantVendor saveVendor(MerchantVendor mv);

    List<UserDTO> getAllManagersForMerchant(String merchantId);
    List<UserDTO> getAllVendorsForMerchant(String merchantId);
}
