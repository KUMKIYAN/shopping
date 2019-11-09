package com.myproduct.orders;

import com.myproduct.lineitem.LineItem;
import com.myproduct.lineitem.LineItemRepository;
import com.myproduct.products.Product;
import com.myproduct.products.ProductRepository;
import com.myproduct.users.User;
import com.myproduct.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sound.sampled.Line;
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

    @Autowired
    private LineItemRepository lineItemRepository;


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

    @RequestMapping(method = RequestMethod.POST,path= "/users/{id}/orders/v2")
    public ResponseEntity<Object> createAOrder(@PathVariable int id, @RequestBody Orders order){
        order.getLineItems().stream().forEach(lineItem -> lineItemRepository.save(lineItem));
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        order.setUser(user);
        order.getLineItems().stream().forEach(lineItem -> {
                 Product product = productRepository.findById(lineItem.getProduct().getId()).get();
                 product.setAvailableQuantity(product.getAvailableQuantity() - lineItem.getQuantity());
                 order.setOrderTotal(order.getOrderTotal() + product.getPrice() * lineItem.getQuantity());
        });
        orderRepository.save(order);
        return new ResponseEntity<>("User " + user.getEmailId() + " created Order:" + order.getId() + " successfully ", HttpStatus.CREATED);
    }
}