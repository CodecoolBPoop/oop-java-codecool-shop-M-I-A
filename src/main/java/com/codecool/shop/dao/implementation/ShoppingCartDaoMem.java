package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.CartItem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDaoMem implements ShoppingCartDao {

    private List<CartItem> shoppingCartItems = new ArrayList<>();
    private static ShoppingCartDaoMem instance = null;

    public static ShoppingCartDaoMem getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(int id, String user, Product product, int count){
        CartItem ct = new CartItem();
        ct.setId(id);
        ct.setProduct(product);
        ct.setUser(user);
        ct.setCount(count);

        shoppingCartItems.add(ct);
    }

    @Override
    public void remove(int id){
        for (CartItem ct : shoppingCartItems){
            if (ct.getId() == id){
                shoppingCartItems.remove(ct);
                break;
            }
        }
    }


    @Override
    public List<CartItem> getByUser(String user){
        List<CartItem> userCart = new ArrayList<>();

        for (CartItem ct : shoppingCartItems){
            if (ct.getUser().equals(user)){
                userCart.add(ct);
            }
        }
        return userCart;
    }

    public List<CartItem> getAll(){
        return shoppingCartItems;
    }
}
