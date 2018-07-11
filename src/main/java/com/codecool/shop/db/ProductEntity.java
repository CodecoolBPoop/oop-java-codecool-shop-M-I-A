package com.codecool.shop.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "public", catalog = "codeshop")
public class ProductEntity {
    private int id;
    private String name;
    private Double price;
    private String currency;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "currency", nullable = true, length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, price, currency);
    }
}
