package com.evertec.ecommerce.controllers;

import com.evertec.ecommerce.dto.UserCreateAndLoginDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.User;
import com.evertec.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateAndLoginDTO userCreateAndLoginDTO){
        return ResponseEntity.ok(userService.createUser(userCreateAndLoginDTO));
    }

    @PostMapping("/login")
    private ResponseEntity<User> loginUser(@Valid @RequestBody UserCreateAndLoginDTO userCreateAndLoginDTO) {
        return ResponseEntity.ok(userService.loginUser(userCreateAndLoginDTO));
    }

    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getOrdersByUserId(userId));
    }
}
