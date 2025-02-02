package com.example.hexagonal.application;

import com.example.hexagonal.domain.model.Order;
import com.example.hexagonal.domain.model.OrderItem;
import com.example.hexagonal.adapters.out.persistence.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OrderService {

    OrderRepository orderRepository;

    @Inject
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(Order order) {
        orderRepository.persist(order);
        return order;
    }

    @Transactional
    public void addItemToOrder(Long orderId, OrderItem item) {
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            order.addItem(item);
            orderRepository.persist(order);
        }
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            order.updateStatus(status);
            orderRepository.persist(order);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.listAll();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
