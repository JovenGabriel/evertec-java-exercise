package com.evertec.ecommerce.controllers;

import com.evertec.ecommerce.dto.OrderUpdateStatusDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OderController {

    private final OrderService orderService;

    @PostMapping("/create/user/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable UUID userId){
        return ResponseEntity.ok(orderService.createOrder(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody OrderUpdateStatusDTO orderUpdateStatusDTO ){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderUpdateStatusDTO));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable UUID orderId ){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
