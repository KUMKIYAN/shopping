package com.myproduct.lineitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproduct.orders.Orders;
import com.myproduct.products.Product;
import io.swagger.models.auth.In;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LineItem {

    @Id
    @GeneratedValue
    private Integer id;
    private int quantity;

    @JsonIgnore
    @ManyToOne
    Orders orders;

    @ManyToOne
    Product product;

    public LineItem() {

    }

    public LineItem(Integer id, int quantity, Orders orders, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.orders = orders;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
