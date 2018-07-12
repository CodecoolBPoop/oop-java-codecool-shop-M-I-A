package com.codecool.shop.model;

public class CartItem {
    private static int nextID = 0;
    private int id = 0;
    private String user;
    private Product product;
    private int count;

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
