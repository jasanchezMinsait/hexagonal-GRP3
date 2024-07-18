package com.example.hexagonal.application.service;

import com.example.hexagonal.application.ports.out.OrderPort;
import com.example.hexagonal.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceV2Test {

    @Mock
    OrderPort orderPort;

    @InjectMocks
    OrderServiceV2 orderServiceV2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order(null, "Test Order", LocalDateTime.now());
        when(orderPort.createOrder(any(Order.class))).thenReturn(order);

        Order savedOrder = orderServiceV2.createOrder(order);

        assertEquals(order.getDescription(), savedOrder.getDescription());
        verify(orderPort, times(1)).createOrder(order);
    }

    @Test
    void testFindAll() {
        Order order = new Order(1L, "Test Order", LocalDateTime.now());
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderPort.findAll()).thenReturn(orders);

        List<Order> ordersSaved = orderServiceV2.findAll();

        assertEquals(orders.size(), ordersSaved.size());
        verify(orderPort, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Order order = new Order(1L, "Test Order", LocalDateTime.now());
        when(orderPort.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> getOrder = orderServiceV2.findById(1L);

        assertEquals(order.getDescription(), getOrder.get().getDescription());
        verify(orderPort, times(1)).findById(order.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(orderPort).deleteById(1L);

        orderServiceV2.deleteById(1L);

        verify(orderPort, times(1)).deleteById(1L);
    }
}