package com.obify.hy.ims.service;

import com.obify.hy.ims.dto.UserDTO;

public interface AuthService {
    String registerUser(UserDTO signUpRequest);
}
