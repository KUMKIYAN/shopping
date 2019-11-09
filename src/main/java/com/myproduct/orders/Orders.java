package com.myproduct.orders;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.myproduct.lineitem.LineItem;
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

    @OneToMany
    List<LineItem> lineItems;

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

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}