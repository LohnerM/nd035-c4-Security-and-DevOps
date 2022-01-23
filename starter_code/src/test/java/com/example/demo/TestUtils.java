package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import org.junit.jupiter.api.Order;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject){

        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);

            if(!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            if(wasPrivate){
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static User createTestUser(){
        long id = 1L;
        id++;
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setId(id);
        user.setCart(new Cart());
        return user;
    }

    public static User createSpecificTestUser(String username){
        long id = 555L;
        User user = new User();
        user.setUsername(username);
        user.setPassword("testPassword");
        user.setId(id);
        user.setCart(new Cart());
        return user;
    }

    public static List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        for (int i=0; i<3; i++) {
            items.add(createItem(i+1L));
        }
        return items;
    }

    public static Item createItem(Long id){
        Item item = new Item();
        item.setId(id);


        item.setPrice(BigDecimal.valueOf(2));
        item.setName("testItem");
        item.setDescription("testDescription");
        return item;
    }

    public static UserOrder createOrder(){
        UserOrder order = new UserOrder();
        User user = createSpecificTestUser("orderUser");
        order.setUser(user);
        order.setId(1L);
        Cart cart = user.getCart();
        cart.addItem(createItem(20L));
        user.setCart(cart);

        order.setItems(cart.getItems());
        order.setTotal(cart.getTotal());

        return order;
    }
}
