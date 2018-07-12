package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
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
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        String selectedCategory = req.getParameter("category");
        String selectedSupplier = req.getParameter("supplier");


//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        System.out.println("itemsInShoppingCart ASD");
        int itemsInShoppingCart = ((ShoppingCartDaoMem) shoppingCartDataStore).getCountByUser("sanya");
        System.out.println("itemsInShoppingCart " + itemsInShoppingCart);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
//        ResultSet rs = ConnectToDB.executeQuery("Select * from product");
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        context.setVariable("cartItems", itemsInShoppingCart);
        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCartDao shoppingCartDataStore = ShoppingCartDaoMem.getInstance();

        System.out.println("getQueryString: " + req.getQueryString());
        System.out.println("getSession: " + req.getSession());

        Integer productToCartId = Integer.valueOf(req.getParameter("addToCart"));

        Product product = productDataStore.find(productToCartId);

        shoppingCartDataStore.add(0, "sanya", product, 1);

        shoppingCartDataStore.listToTerminal();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        ResultSet rs = ConnectToDB.executeQuery("Select * from product");
        context.setVariable("recipient", "World");
        context.setVariable("cartItems", ((ShoppingCartDaoMem) shoppingCartDataStore).getCountByUser("sanya"));

        ProductCategory productCat = productCategoryDataStore.find(Integer.parseInt(selectedCategory == null ? "1" : selectedCategory));
        Supplier chosenSupp = supplierDataStore.find(Integer.parseInt(selectedSupplier));

        context.setVariable("products", productDataStore.getBy(productCat, chosenSupp));
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        engine.process("product/index.html", context, resp.getWriter());
    }
}

