package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.User;
import com.evertec.ecommerce.exceptions.NotFoundException;
import com.evertec.ecommerce.repositories.OrderRepository;
import com.evertec.ecommerce.repositories.UserRepository;
import com.evertec.ecommerce.utils.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    public OrderServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the creation of an order when the user exists.
     * <p>
     * This method verifies that when a valid user ID is provided, an order is successfully
     * created with the correct user association and initial order status. The test also ensures
     * that the appropriate repository methods are called to interact with the user and order data.
     * <p>
     * Test steps:
     * 1. Arrange: Set up a mock user and order instance. Configure mocks to return the user
     *    when searched by ID and save the order successfully.
     * 2. Act: Invoke the `createOrder` method of the service.
     * 3. Assert:
     *    - Ensure the created order is not null.
     *    - Verify that the created order is associated with the correct user.
     *    - Confirm that the order has the expected status of PENDING.
     *    - Validate proper repository method invocations for finding the user by ID and saving the order.
     */
    @Test
    void createOrder_ShouldCreateOrderWhenUserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).build();
        Order order = Order.builder().user(user).orderStatus(OrderStatus.PENDING).build();

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order createdOrder = orderService.createOrder(userId);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(user, createdOrder.getUser());
        assertEquals(OrderStatus.PENDING, createdOrder.getOrderStatus());
        verify(userRepository, times(1)).findUserById(userId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    /**
     * Unit test for the createOrder method of the OrderServiceImpl class, validating that a
     * NotFoundException is thrown when attempting to create an order for a user that does not exist.
     * <p>
     * This test ensures the following behavior:
     * - When the user with the specified ID cannot be found in the UserRepository, a NotFoundException is thrown.
     * - The exception message matches the expected format with the user's ID included.
     * - The findUserById method of the UserRepository is invoked exactly once during the operation.
     * - The save method of the OrderRepository is never called, as no order should be created for a non-existent user.
     * <p>
     * Dependencies such as UserRepository and OrderRepository are mocked using Mockito to isolate the
     * behavior of the method under test and confirm its interaction with those dependencies.
     */
    @Test
    void createOrder_ShouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderService.createOrder(userId));
        assertEquals("User Not Found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findUserById(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }
}