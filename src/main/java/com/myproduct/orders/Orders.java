package com.myproduct.orders;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproduct.products.Product;
import com.myproduct.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name="seq", initialValue=1001, allocationSize=2000)
public class Orders {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Integer id;
    private String paymentMethod;
    private float orderTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}