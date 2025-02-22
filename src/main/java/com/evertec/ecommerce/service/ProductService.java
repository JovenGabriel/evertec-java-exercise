package com.evertec.ecommerce.service;

import com.evertec.ecommerce.dto.ProductDTO;
import com.evertec.ecommerce.entities.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDTO productDTO);
    List<Product> getAllProducts();
}
