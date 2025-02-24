package com.evertec.ecommerce.service;

import com.evertec.ecommerce.dto.OrderUpdateStatusDTO;
import com.evertec.ecommerce.entities.Order;

import java.util.UUID;

public interface OrderService {

    Order createOrder(UUID userId);
    Order getOrderById(UUID orderId);
    Order updateOrderStatus(OrderUpdateStatusDTO orderUpdateStatusDTO);
    Order cancelOrder(UUID orderId);
}
