package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.OrderUpdateStatusDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.User;
import com.evertec.ecommerce.exceptions.NotFoundException;
import com.evertec.ecommerce.repositories.OrderRepository;
import com.evertec.ecommerce.repositories.UserRepository;
import com.evertec.ecommerce.service.OrderService;
import com.evertec.ecommerce.utils.OrderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new order for a given user with an initial status of "PENDING".
     *
     * @param userId the unique identifier of the user for whom the order is created
     * @return the created Order instance
     * @throws NotFoundException if the user with the specified ID does not exist
     */
    @Override
    @Transactional
    public Order createOrder(UUID userId) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new NotFoundException("User Not Found with id: " + userId));
        return orderRepository.save(Order.builder()
                .user(user)
                .orderStatus(OrderStatus.PENDING)
                .build());
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return the order associated with the provided identifier
     * @throws NotFoundException if no order is found with the given identifier
     */
    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order Not Found with id: " + orderId));
    }

    /**
     * Updates the status of an order based on the provided details.
     *
     * @param orderUpdateStatusDTO the data transfer object containing the order ID
     *                             and the new status for the order
     * @return the updated Order entity after applying the new status
     * @throws NotFoundException if the order with the specified ID is not found
     */
    @Override
    @Transactional
    public Order updateOrderStatus(OrderUpdateStatusDTO orderUpdateStatusDTO) {
        Order order = orderRepository.findById(orderUpdateStatusDTO.getOrderId()).orElseThrow(() -> new NotFoundException("Order Not Found with id: " + orderUpdateStatusDTO.getOrderId()));
        order.setOrderStatus(orderUpdateStatusDTO.getStatus());
        return orderRepository.save(order);
    }

    /**
     * Cancels an existing order by updating its status to "CANCELLED".
     *
     * @param orderId the unique identifier of the order to be canceled
     * @return the updated {@link Order} with its status set to "CANCELLED"
     * @throws NotFoundException if no order is found with the specified ID
     */
    @Override
    @Transactional
    public Order cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order Not Found with id: " + orderId));
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
