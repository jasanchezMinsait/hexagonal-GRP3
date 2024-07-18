package com.example.hexagonal.adapter.out.persistence;

import com.example.hexagonal.domain.model.Order;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@Transactional
class JpaOrderRepositoryV2Test {

    @Inject
    JpaOrderRepositoryV2 repo;

    @Inject
    EntityManager em;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Order order = new Order(null, "Test Order", LocalDateTime.now());
        em.persist(order);
        em.flush();
        em.clear();

        Optional<Order> order2 = repo.findById(order.getId());

        assertTrue(order2.isPresent());
        assertEquals(order.getId(), order2.get().getId());
        assertEquals(order.getDescription(), order2.get().getDescription());

    }

    @Test
    void findAll() {
    }

    @Test
    void testCreateOrder() {
        //Order order = new Order(null, "Test Order", LocalDateTime.now());

        //doNothing().when(em).persist(order);

        //Order orderResult = repo.createOrder(order);

        //verify(em, times(1)).persist(order);
        //assertEquals(order, orderResult);


        Order order = new Order(null, "Test Order", LocalDateTime.now());
        repo.createOrder(order);
        em.flush();
        em.clear();

        Optional<Order> savedOrder = repo.findById(order.getId());
        repo.createOrder(order);
        assertTrue(savedOrder.isPresent());
        assertEquals(order.getDescription(), savedOrder.get().getDescription());

    }

    @Test
    void deleteById() {
    }
}