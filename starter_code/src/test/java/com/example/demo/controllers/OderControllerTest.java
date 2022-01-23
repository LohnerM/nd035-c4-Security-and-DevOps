package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setup(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

    }

    UserOrder ORDER = TestUtils.createOrder();

    @Test
    public void verify_submit(){


        when(userRepository.findByUsername("orderUser")).thenReturn(ORDER.getUser());
        ResponseEntity<UserOrder> response = orderController.submit(ORDER.getUser().getUsername());

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder newOrder = response.getBody();
        assertNotNull(newOrder);
        assertEquals(ORDER.getTotal(), newOrder.getTotal());
        assertEquals(ORDER.getItems(), newOrder.getItems());

    }

    @Test
    public void verify_getOrdersForUser(){
        when(userRepository.findByUsername("orderUser")).thenReturn(ORDER.getUser());
        List<UserOrder> orderList = new ArrayList<>();
        orderList.add(ORDER);
        when(orderRepository.findByUser(any())).thenReturn((orderList));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(ORDER.getUser().getUsername());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<UserOrder> newOrderList = response.getBody();
        assertNotNull(newOrderList);
        assertEquals(ORDER.getItems().size(), newOrderList.size());

    }
}
