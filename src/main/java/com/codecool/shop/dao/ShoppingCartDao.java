package com.codecool.shop.dao;

import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.Product;

import java.util.List;

public interface ShoppingCartDao {

    void add(int id, String user, Product product, int count);
    void remove(int id);

    List<CartItem> getByUser(String user);
}
