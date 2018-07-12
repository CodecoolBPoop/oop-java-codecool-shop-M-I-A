package com.codecool.shop.db;

import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectToDB {
    private static final String DATABASE = System.getenv("DATABASE");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public static List<Map<String,Object>> executeQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs;
        MapListHandler mapListHandler = new MapListHandler();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            list = mapListHandler.handle(rs);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ignored) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return list;
    }
}
