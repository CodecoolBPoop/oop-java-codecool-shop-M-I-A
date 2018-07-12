package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.db.ConnectToDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDaoDB implements ProductDao {

    private static ProductDaoDB instance = null;

    private ProductDaoDB() {}

    public static ProductDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductDaoDB();
        }
        return instance;
    }

    private static Product parseProductFromMap(Map<String, Object> product) {
        return new Product(product.get("name").toString(),
                Float.parseFloat(product.get("price").toString()),
                product.get("currency").toString(),
                product.get("description").toString(),
                ProductCategoryDaoDB.getInstance().find(Integer.parseInt(product.get("category").toString())),
                SupplierDaoDB.getInstance().find(Integer.parseInt(product.get("supplier").toString())));
    }

    private static List<Product> parseMapListOfProducts(List<Map<String, Object>> mapList) {
        List<Product> productList = new ArrayList<>();
        for (Map<String, Object> row : mapList) {
            productList.add(parseProductFromMap(row));
        }
        return productList;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO product VALUES (" +
                product.getName() + ", " +
                product.getPrice() + ", " +
                product.getDefaultCurrency() + ", " +
                product.getProductCategory().getId() + " ," +
                product.getSupplier().getId() + ", " +
                product.getDescription() + ")";
        ConnectToDB.executeQuery(query);
    }

    @Override
    public Product find(int id) {
        String query = "SELECT * FROM product WHERE id = " + id;
        return parseProductFromMap(ConnectToDB.executeQuery(query).get(0));
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM product WHERE id = " + id;
        ConnectToDB.executeQuery(query);
    }

    @Override
    public List<Product> getAll() {
        String query = "Select * from product";
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = "SELECT * FROM product WHERE supplier = " + supplier.getId();
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "SELECT * FROM product WHERE category = " + productCategory.getId();
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        String query = "SELECT * FROM product WHERE category = " + productCategory.getId() + " AND supplier = " + supplier.getId();
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }
}
