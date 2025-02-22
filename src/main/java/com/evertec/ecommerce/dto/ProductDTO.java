package com.evertec.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {
    
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.")
    private double price;
}
