package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.Item;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setup(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void verify_getItems(){
        when(itemRepository.findAll()).thenReturn(TestUtils.createItems());
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void verify_getItemById(){
        when(itemRepository.findById(5L)).thenReturn(Optional.of(TestUtils.createItem(5L)));
        ResponseEntity<Item> response = itemController.getItemById(5L);
        assertNotNull(response);
        Long id = 5L;
        assertEquals(id, response.getBody().getId());
    }

    @Test
    public void verify_getItemsByName(){
        when(itemRepository.findByName("testItem")).thenReturn(Arrays.asList(TestUtils.createItem(7L), TestUtils.createItem(8L)));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("testItem");
        assertNotNull(response);
        List<Item> itemList = response.getBody();
        assertEquals(2, itemList.size());
        assertEquals("testItem", itemList.get(0).getName());
    }






}
