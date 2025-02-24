package com.evertec.ecommerce.service;

import com.evertec.ecommerce.dto.UserCreateAndLoginDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(UserCreateAndLoginDTO userCreateAndLoginDTO);
    User loginUser(UserCreateAndLoginDTO userCreateAndLoginDTO);
    List<Order> getOrdersByUserId(UUID userId);

}
