package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.ProductDTO;
import com.evertec.ecommerce.entities.Product;
import com.evertec.ecommerce.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.evertec.ecommerce.repositories.ProductRepository;
import com.evertec.ecommerce.service.ProductService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Creates and saves a new product entity in the database using the provided ProductDTO.
     *
     * @param productDTO the Data Transfer Object (DTO) containing product information such as name, description, and price
     * @return the newly created and saved Product instance
     */
    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        return productRepository.save(Product.builder().name(productDTO
                .getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build());
    }

    /**
     * Retrieves a list of all available products from the database.
     *
     * @return a list of {@link Product} objects representing all the products in the repository
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param productId the unique identifier of the product to retrieve
     * @return the {@link Product} entity matching the given identifier
     * @throws NotFoundException if no product is found with the specified identifier
     */
    @Override
    @Transactional(readOnly = true)
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
    }
}
