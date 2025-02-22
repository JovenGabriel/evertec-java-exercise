package com.evertec.ecommerce.service;

import com.evertec.ecommerce.dto.UserCreateAndLoginDTO;
import com.evertec.ecommerce.dto.UserDTO;
import com.evertec.ecommerce.entities.User;

public interface UserService {

    UserDTO createUser(UserCreateAndLoginDTO userCreateAndLoginDTO);
    User loginUser(String email, String password);
}
