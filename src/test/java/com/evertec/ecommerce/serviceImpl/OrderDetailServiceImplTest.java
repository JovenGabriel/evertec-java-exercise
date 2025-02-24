package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.OrderDetailCreateDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.OrderDetail;
import com.evertec.ecommerce.entities.Product;
import com.evertec.ecommerce.exceptions.NotFoundException;
import com.evertec.ecommerce.repositories.OrderDetailRepository;
import com.evertec.ecommerce.repositories.OrderRepository;
import com.evertec.ecommerce.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class OrderDetailServiceImplTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailServiceImpl;

    OrderDetailServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the success scenario of creating a new OrderDetail.
     *
     * The test verifies the following:
     * 1. An OrderDetail entity is created with the correct order ID, product ID, and quantity specified in the input data transfer object.
     * 2. The corresponding repositories (`orderRepository`, `productRepository`, and `orderDetailRepository`) are called the expected number of times.
     * 3. The returned OrderDetail contains the expected field values.
     *
     * Test Steps:
     * - Mock the retrieval of an existing Order and Product through their respective repositories.
     * - Create a new OrderDetailCreateDTO containing the necessary information for creating an OrderDetail.
     * - Mock the save operation of the OrderDetail in the repository.
     * - Call the service method `createOrderDetail` to perform the operation.
     * - Validate the output to ensure that the created OrderDetail matches the expected values.
     * - Verify that the repositories are appropriately interacted with during the process.
     *
     * Assertions:
     * - The returned OrderDetail has matching order ID, product ID, and quantity to the input values.
     * - `orderRepository.findById` is called exactly once.
     * - `productRepository.findById` is called exactly once.
     * - `orderDetailRepository.save` is called exactly once.
     */
    @Test
    @DisplayName("Should Successfully Create OrderDetail")
    void testCreateOrderDetailSuccess() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Order order = Order.builder().id(orderId).build();
        Product product = Product.builder().id(productId).build();

        OrderDetailCreateDTO createDTO = new OrderDetailCreateDTO();
        createDTO.setOrderId(orderId);
        createDTO.setProductId(productId);
        createDTO.setQuantity(2);

        OrderDetail orderDetail = OrderDetail.builder()
                .id(UUID.randomUUID())
                .order(order)
                .product(product)
                .quantity(createDTO.getQuantity())
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        OrderDetail result = orderDetailServiceImpl.createOrderDetail(createDTO);

        assertEquals(orderId, result.getOrder().getId());
        assertEquals(productId, result.getProduct().getId());
        assertEquals(createDTO.getQuantity(), result.getQuantity());

        verify(orderRepository, times(1)).findById(orderId);
        verify(productRepository, times(1)).findById(productId);
        verify(orderDetailRepository, times(1)).save(any(OrderDetail.class));
    }

    /**
     * Tests the scenario where an attempt to create an OrderDetail fails because the associated Order entity is not found in the database.
     *
     * Scenario:
     * - A new OrderDetailCreateDTO object is created with a random orderId, productId, and a valid quantity.
     * - The `orderRepository.findById` method is mocked to return an empty `Optional`, simulating a missing order.
     * - The `orderDetailServiceImpl.createOrderDetail` method is invoked, and a `NotFoundException` is expected.
     *
     * Verifications:
     * - Asserts that the `NotFoundException` is thrown with the correct message, indicating that the order with the specified ID does not exist.
     * - Ensures that the `orderRepository.findById` method is called exactly once with the expected orderId.
     * - Confirms that `productRepository.findById` and `orderDetailRepository.save` are never invoked, as the process fails due to the missing order.
     */
    @Test
    @DisplayName("Should Throw Exception When Order Not Found")
    void testCreateOrderDetailOrderNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        OrderDetailCreateDTO createDTO = new OrderDetailCreateDTO();
        createDTO.setOrderId(orderId);
        createDTO.setProductId(productId);
        createDTO.setQuantity(2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderDetailServiceImpl.createOrderDetail(createDTO));

        assertEquals("Order not found with id: " + orderId, exception.getMessage());

        verify(orderRepository, times(1)).findById(orderId);
        verify(productRepository, never()).findById(any());
        verify(orderDetailRepository, never()).save(any());
    }

    /**
     * Tests the scenario where an attempt is made to create an OrderDetail entry
     * for a product that cannot be found in the database.
     *
     * Verifies that:
     * - An exception of type {@link NotFoundException} is thrown when the product
     *   with the specified ID does not exist.
     * - The exception message correctly identifies the missing product.
     * - The correct interactions occur with the mocked repositories:
     *   - The {@code orderRepository.findById} method is called once.
     *   - The {@code productRepository.findById} method is called once.
     *   - The {@code orderDetailRepository.save} method is never called.
     *
     * Preconditions:
     * - A valid {@link OrderDetailCreateDTO} object is provided, with valid order
     *   and product IDs, and a valid quantity.
     * - The order exists in the system but the product ID does not exist.
     *
     * Expected Outcome:
     * - The test should pass if the appropriate exception is thrown and
     *   the repository methods are called as expected.
     */
    @Test
    @DisplayName("Should Throw Exception When Product Not Found")
    void testCreateOrderDetailProductNotFound() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Order order = Order.builder().id(orderId).build();

        OrderDetailCreateDTO createDTO = new OrderDetailCreateDTO();
        createDTO.setOrderId(orderId);
        createDTO.setProductId(productId);
        createDTO.setQuantity(2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderDetailServiceImpl.createOrderDetail(createDTO));

        assertEquals("Product not found with id: " + productId, exception.getMessage());

        verify(orderRepository, times(1)).findById(orderId);
        verify(productRepository, times(1)).findById(productId);
        verify(orderDetailRepository, never()).save(any());
    }
}