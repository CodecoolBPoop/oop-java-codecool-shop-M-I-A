package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.db.ConnectToDB;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.stream.Stream;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoDB.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoDB.getInstance();

        String selectedCategory = req.getParameter("category");
        String selectedSupplier = req.getParameter("supplier");


//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));


        int itemsInShoppingCart = ((ShoppingCartDaoMem) shoppingCartDataStore).getCountByUser("sanya");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
//        ResultSet rs = ConnectToDB.executeQuery("Select * from product");


        ProductCategory productCat = productCategoryDataStore.find(Integer.parseInt(selectedCategory == null ? "1" : selectedCategory));
        Supplier chosenSupp = supplierDataStore.find(Integer.parseInt(selectedSupplier == null ? "1" : selectedSupplier));

        if(selectedCategory == null && selectedSupplier == null){
            context.setVariable("products", productDataStore.getAll());
            System.out.println("SELECTIONS NULL");
        } else {
            context.setVariable("products", productDataStore.getBy(productCat, chosenSupp));
            System.out.println("SELECTIONS NOT NULL");
        }

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("recipient", "World");
        context.setVariable("cartItems", itemsInShoppingCart);
        engine.process("product/indexv2.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoDB.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();

        Integer productToCartId = Integer.valueOf(req.getParameter("addToCart"));

        Product product = productDataStore.find(productToCartId);

        shoppingCartDataStore.add(0, "sanya", product, 1);

        this.doGet(req, resp);
    }
}

