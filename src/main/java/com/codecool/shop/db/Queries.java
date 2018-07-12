package com.codecool.shop.db;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Queries {

    private static Product parseProductFromMap(Map<String, Object> product) {
        return new Product(product.get("name").toString(),
                Float.parseFloat(product.get("price").toString()),
                product.get("currency").toString(),
                product.get("description").toString(),
                Queries.getCategoryById(Integer.parseInt(product.get("category").toString())),
                Queries.getSupplierById(Integer.parseInt(product.get("supplier").toString())));
    }

    private static Supplier parseSupplierFromMap(Map<String, Object> supplier) {
        return new Supplier(Integer.parseInt(supplier.get("id").toString()),
                supplier.get("name").toString(),
                supplier.get("description").toString());
    }

    private static ProductCategory parseProductCategoryFromMap(Map<String, Object> category) {
        return new ProductCategory(
                Integer.parseInt(category.get("id").toString()),
                category.get("name").toString(),
                category.get("department").toString(),
                category.get("description").toString());
    }

    private static List<Product> parseMapListOfProducts(List<Map<String, Object>> mapList) {
        List<Product> productList = new ArrayList<>();
        for (Map<String, Object> row : mapList) {
            productList.add(parseProductFromMap(row));
        }
        return productList;
    }

    public static List<Product> getAllProducts() {
        String query = "Select * from product";
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }

    public static Supplier getSupplierById(int id) {
        String query = "SELECT * FROM supplier WHERE id = " + id;
        return parseSupplierFromMap(ConnectToDB.executeQuery(query).get(0));
    }

    public static ProductCategory getCategoryById(int id) {
        String query = "SELECT * FROM product_category WHERE id = " + id;
        return parseProductCategoryFromMap(ConnectToDB.executeQuery(query).get(0));
    }

    public static List<Product> getProductsByCategory(ProductCategory category) {
        String query = "SELECT * FROM product WHERE category = " + category.getId();
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }

    public static List<Product> getProductsBySupplier(Supplier supplier) {
        String query = "SELECT * FROM product WHERE supplier = " + supplier.getId();
        return parseMapListOfProducts(ConnectToDB.executeQuery(query));
    }

    public static Product findProductById(int id) {
        String query = "SELECT * FROM product WHERE id = " + id;
        return parseProductFromMap(ConnectToDB.executeQuery(query).get(0));
    }

}
