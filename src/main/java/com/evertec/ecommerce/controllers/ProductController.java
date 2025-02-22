package com.evertec.ecommerce.controllers;

import com.evertec.ecommerce.dto.ProductDTO;
import com.evertec.ecommerce.entities.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evertec.ecommerce.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
    }
}
