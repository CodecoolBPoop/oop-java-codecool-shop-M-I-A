package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.db.ConnectToDB;
import com.codecool.shop.model.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductCategoryDaoDB implements ProductCategoryDao {
    private static ProductCategoryDaoDB instance = null;

    public static ProductCategoryDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoDB();
        }
        return instance;
    }

    private static ProductCategory parseProductCategoryFromMap(Map<String, Object> category) {
        return new ProductCategory(
                Integer.parseInt(category.get("id").toString()),
                category.get("name").toString(),
                category.get("department").toString(),
                category.get("description").toString());
    }

    private static List<ProductCategory> parseMapListOfProductCategories(List<Map<String, Object>> mapList) {
        List<ProductCategory> categoryList = new ArrayList<>();
        for (Map<String, Object> row : mapList) {
            categoryList.add(parseProductCategoryFromMap(row));
        }
        return categoryList;
    }

    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO product_category VALUES ("+ category.getName() + "," + category.getDepartment() + "," + category.getDescription() + ")";
        ConnectToDB.executeQuery(query);
    }

    @Override
    public ProductCategory find(int id) {
        String query = "SELECT * FROM product_category WHERE id = " + id;
        return parseProductCategoryFromMap(ConnectToDB.executeQuery(query).get(0));
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM product_category WHERE id =" + id;
        ConnectToDB.executeQuery(query);
    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM product_category";
        return parseMapListOfProductCategories(ConnectToDB.executeQuery(query));
    }
}
