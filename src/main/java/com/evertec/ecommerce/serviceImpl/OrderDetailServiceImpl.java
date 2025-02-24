package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.OrderDetailCreateDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.OrderDetail;
import com.evertec.ecommerce.entities.Product;
import com.evertec.ecommerce.exceptions.NotFoundException;
import com.evertec.ecommerce.repositories.OrderDetailRepository;
import com.evertec.ecommerce.repositories.OrderRepository;
import com.evertec.ecommerce.repositories.ProductRepository;
import com.evertec.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * Creates a new OrderDetail entity based on the provided data transfer object.
     *
     * @param orderDetailCreateDTO the data transfer object containing information about the order detail to be created, including the order ID, product ID, and quantity
     * @return the newly created OrderDetail entity
     * @throws NotFoundException if the order or product with the provided IDs do not exist
     */
    @Override
    public OrderDetail createOrderDetail(OrderDetailCreateDTO orderDetailCreateDTO) {
        Order order = orderRepository.findById(orderDetailCreateDTO.getOrderId()).orElseThrow(() -> new NotFoundException("Order not found with id: " + orderDetailCreateDTO.getOrderId()));
        Product product = productRepository.findById(orderDetailCreateDTO.getProductId()).orElseThrow(() -> new NotFoundException("Product not found with id: " + orderDetailCreateDTO.getProductId()));
        return orderDetailRepository.save(OrderDetail.builder()
                        .order(order)
                        .product(product)
                        .quantity(orderDetailCreateDTO.getQuantity())
                .build());
    }
}
