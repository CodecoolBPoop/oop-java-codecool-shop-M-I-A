package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.db.ConnectToDB;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SupplierDaoDB implements SupplierDao {

    private static SupplierDaoDB instance = null;

    public static SupplierDaoDB getInstance() {
        if (instance == null) {
            instance = new SupplierDaoDB();
        }
        return instance;
    }

    private static Supplier parseSupplierFromMap(Map<String, Object> supplier) {
        return new Supplier(Integer.parseInt(supplier.get("id").toString()),
                supplier.get("name").toString(),
                supplier.get("description").toString());
    }

    private List<Supplier> parseMapListOfSuppliers(List<Map<String, Object>> mapList) {
        List<Supplier> suppliers = new ArrayList<>();
        for (Map<String, Object> row : mapList) {
            suppliers.add(parseSupplierFromMap(row));
        }
        return suppliers;
    }

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO supplier VALUES (" + supplier.getName() + ", " + supplier.getDescription() + ")";
        ConnectToDB.executeQuery(query);
    }

    @Override
    public Supplier find(int id) {
        String query = "SELECT * FROM supplier WHERE id = " + id;
        return parseSupplierFromMap(ConnectToDB.executeQuery(query).get(0));
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM supplier WHERE id = " + id;
        ConnectToDB.executeQuery(query);
    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM supplier";
        return parseMapListOfSuppliers(ConnectToDB.executeQuery(query));
    }


}
