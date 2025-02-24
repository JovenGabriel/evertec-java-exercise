package com.evertec.ecommerce.service;

import com.evertec.ecommerce.dto.OrderDetailCreateDTO;
import com.evertec.ecommerce.entities.OrderDetail;

public interface OrderDetailService {

    OrderDetail createOrderDetail(OrderDetailCreateDTO orderDetailCreateDTO);
}
