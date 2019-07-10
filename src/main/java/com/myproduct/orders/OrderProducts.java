package com.myproduct.orders;

import com.myproduct.products.Product;

import java.util.List;

public class OrderProducts {
    private Orders orders;
    private List<Product> products;
    private Integer quantity;

    public OrderProducts(Orders orders, List<Product> products, Integer quanity) {
        this.orders = orders;
        this.products = products;
        this.quantity = quanity;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrdersProduct{" +
                "orders=" + orders +
                ", products=" + products +
                ", quantity=" + quantity +
                '}';
    }
}
