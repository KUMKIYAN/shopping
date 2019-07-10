package com.myproduct.orders;

import com.myproduct.products.Product;
import com.myproduct.products.ProductRepository;
import com.myproduct.users.User;
import com.myproduct.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping(method = RequestMethod.GET,path= "/users/{id}/orders")
    public List<Orders> getAnUserOrder(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.get().getOrders();
    }

    @RequestMapping(method = RequestMethod.GET,path= "/orders")
    public List<Orders> getAllOrder(){
        return orderRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST,path= "/users/{id}/orders")
    public ResponseEntity<Object> createAnOrder(@PathVariable int id, @RequestBody Orders order){
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        order.setUser(user);
        orderRepository.save(order);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId()).toUri();

        ResponseEntity.created(location).build();
        return new ResponseEntity<>("User " + user.getEmailId() + " created Order: " + order.getId() + " successfully ", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST,path= "/users/{id}/orders/v1")
    public ResponseEntity<Object> createAOrder(@PathVariable int id, @RequestBody Orders order){
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        order.setUser(user);

        Optional<Product> productOptional = productRepository.findById(order.getProducts().stream().findFirst().get().getId());
        Product product = productOptional.get();
        product.setQty(order.getProducts().stream().findFirst().get().getQty());
        System.out.println(product);
        System.out.println(product.getPrice() * product.getQty());
        order.setOrderTotal(product.getPrice() * product.getQty());
        orderRepository.save(order);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId()).toUri();

        ResponseEntity.created(location).build();
        return new ResponseEntity<>("User " + user.getEmailId() + " created Order:" + order.getId() + " successfully ", HttpStatus.CREATED);
    }
}