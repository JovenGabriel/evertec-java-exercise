package com.evertec.ecommerce.dto;

import com.evertec.ecommerce.annotations.EnumValidator;
import com.evertec.ecommerce.utils.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderUpdateStatusDTO {
    
    @NotBlank
    private UUID orderId;
    @NotBlank
    @EnumValidator(enumClass = OrderStatus.class)
    private OrderStatus status;
}
