package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void verify_addToCart() {
        when(userRepository.findByUsername("testUser")).thenReturn(TestUtils.createTestUser());
        when(itemRepository.findById(any())).thenReturn(Optional.of(TestUtils.createItem(10L)));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        User user = TestUtils.createTestUser();
        modifyCartRequest.setUsername(user.getUsername());
        Item item = TestUtils.createItem(10L);
        modifyCartRequest.setItemId(item.getId());
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(1, cart.getItems().size());
    }

    @Test
    public void verify_removeFromCart(){
        when(userRepository.findByUsername("testUser")).thenReturn(TestUtils.createTestUser());
        when(itemRepository.findById(any())).thenReturn(Optional.of(TestUtils.createItem(10L)));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        User user = TestUtils.createTestUser();
        modifyCartRequest.setUsername(user.getUsername());
        Item item = TestUtils.createItem(10L);
        modifyCartRequest.setItemId(item.getId());
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(0, cart.getItems().size());
    }



}
