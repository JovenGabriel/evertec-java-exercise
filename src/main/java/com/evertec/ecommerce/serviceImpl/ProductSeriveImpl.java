package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.ProductDTO;
import com.evertec.ecommerce.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.evertec.ecommerce.repositories.ProductRepository;
import com.evertec.ecommerce.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSeriveImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        return productRepository.save(Product.builder().name(productDTO
                .getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build());
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
