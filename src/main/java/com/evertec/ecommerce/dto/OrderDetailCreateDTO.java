package com.evertec.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailCreateDTO {
    
    @NotBlank
    private UUID productId;
    @NotBlank
    private UUID orderId;
    @NotNull
    @DecimalMin(value = "1", message = "Quantity must be greater than or equal to 1")
    private int quantity;
}
