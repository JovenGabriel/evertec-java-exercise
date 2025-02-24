package com.evertec.ecommerce.service;

import com.evertec.ecommerce.dto.ProductDTO;
import com.evertec.ecommerce.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product createProduct(ProductDTO productDTO);
    List<Product> getAllProducts();
    Product getProductById(UUID productId);
}
