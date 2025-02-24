package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.ProductDTO;
import com.evertec.ecommerce.entities.Product;
import com.evertec.ecommerce.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    /**
     * Tests the successful creation of a product using the ProductService.
     * <p>
     * The test validates that a product is correctly created and saved by:
     * - Setting up a mock input ProductDTO with name, description, and price.
     * - Mocking the behavior of the productRepository to save a predefined Product instance.
     * - Calling the createProduct method of the ProductService implementation.
     * - Asserting that the returned Product instance matches the saved mock Product in all fields (name, description, price, and id).
     * <p>
     * This test ensures the ProductService correctly translates data from a ProductDTO to a Product entity,
     * interacts with the productRepository's save method, and returns the expected Product instance.
     */
    @Test
    @DisplayName("Test createProduct - Success")
    void testCreateProductSuccess() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(10.5);

        Product savedProduct = Product.builder()
                .id(UUID.randomUUID())
                .name("Test Product")
                .description("Test Description")
                .price(10.5)
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        Product result = productService.createProduct(productDTO);

        // Assert
        assertEquals(savedProduct.getName(), result.getName());
        assertEquals(savedProduct.getDescription(), result.getDescription());
        assertEquals(savedProduct.getPrice(), result.getPrice());
        assertEquals(savedProduct.getId(), result.getId());
    }
}