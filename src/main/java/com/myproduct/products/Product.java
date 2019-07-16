package com.myproduct.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.myproduct.orders.Orders;
import com.myproduct.users.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@JsonPropertyOrder(value = {
        "id",
        "name",
        "price"
})
@Entity
public class Product {

    @Id
    private Integer id;
    private String name;
    private float price;
    private Integer qty;

    public Product(){}


    public Product(Integer id, String name, float price, Integer qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", qty=" + qty + " kkkkkk" +
                '}';
    }
}