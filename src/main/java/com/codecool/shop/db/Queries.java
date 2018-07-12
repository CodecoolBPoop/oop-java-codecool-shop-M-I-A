package com.codecool.shop.db;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Queries {

    public static List<Product> getAllProducts() {
        String query = "Select * from product";
        List<Product> productList = new ArrayList<>();
        List<Map<String, Object>> result = ConnectToDB.executeQuery(query);
        for (Map<String, Object> row: result) {
            productList.add(new Product(row.get("name").toString(),
                    Float.parseFloat(row.get("price").toString()),
                    row.get("currency").toString(),
                    row.get("description").toString(),
                    Queries.getCategoryById(Integer.parseInt(row.get("category").toString())),
                    Queries.getSupplierById(Integer.parseInt(row.get("supplier").toString()))));
        }
        return productList;
    }

    public static Supplier getSupplierById(int id) {
       String query = "SELECT * FROM supplier WHERE id = " + id;
        Map<String, Object> result = ConnectToDB.executeQuery(query).get(0);
        return new Supplier(Integer.parseInt(result.get("id").toString()),
                            result.get("name").toString(),
                            result.get("description").toString());
    }

    public static ProductCategory getCategoryById(int id) {
        String query = "SELECT * FROM product_category WHERE id = " + id;
        Map<String, Object> result = ConnectToDB.executeQuery(query).get(0);
        return new ProductCategory(
                Integer.parseInt(result.get("id").toString()),
                result.get("name").toString(),
                result.get("department").toString(),
                result.get("description").toString());
    }


}
