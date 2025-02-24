package com.evertec.ecommerce.controllers;

import com.evertec.ecommerce.dto.OrderDetailCreateDTO;
import com.evertec.ecommerce.entities.OrderDetail;
import com.evertec.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ordersDetails")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<OrderDetail> creatOrderDetail(OrderDetailCreateDTO orderDetailCreateDTO){
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailCreateDTO));
    }
}
