package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.UserDTO;
import com.obify.hy.ims.entity.MerchantManager;
import com.obify.hy.ims.entity.MerchantVendor;
import com.obify.hy.ims.entity.User;
import com.obify.hy.ims.repository.MerchantManagerRepository;
import com.obify.hy.ims.repository.MerchantVendorRepository;
import com.obify.hy.ims.repository.UserRepository;
import com.obify.hy.ims.service.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    UserRepository userRepository;
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

    @Override
    public List<UserDTO> getAllManagersForMerchant(String merchantId) {
        List<MerchantManager> managers = merchantManagerRepository.findAllByMerchantId(merchantId);
        List<UserDTO> users = new ArrayList<>();
        UserDTO userDTO = null;
        for(MerchantManager mm: managers){
            User user = userRepository.findById(mm.getManagerId()).get();
            user.setPassword(null);
            userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            users.add(userDTO);
        }
        return users;
    }

    @Override
    public List<UserDTO> getAllVendorsForMerchant(String merchantId) {
        List<MerchantVendor> vendors = merchantVendorRepository.findAllByMerchantId(merchantId);
        List<UserDTO> users = new ArrayList<>();
        UserDTO userDTO = null;
        for(MerchantVendor mv: vendors){
            User user = userRepository.findById(mv.getVendorId()).get();
            user.setPassword(null);
            userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            users.add(userDTO);
        }
        return users;
    }
}
