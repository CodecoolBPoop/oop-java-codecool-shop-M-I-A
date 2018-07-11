package com.codecool.shop.db;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "order", schema = "public", catalog = "codeshop")
public class OrderEntity {
    private int id;
    private Double productPrice;
    private Integer state;
    private String session;
    private Timestamp date;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Basic
    @Column(name = "product_price", nullable = true, precision = 0)
    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @Basic
    @Column(name = "state", nullable = true)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "session", nullable = false, length = -1)
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
