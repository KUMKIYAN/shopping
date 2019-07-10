package com.myproduct.users;

import com.myproduct.orders.OrderRepository;
import com.myproduct.orders.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET,path= "/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET,path= "/users/{id}")
    public User getAnUser(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    @RequestMapping(method = RequestMethod.POST,path= "/users")
    public ResponseEntity<Object> addAnUser(@Valid @RequestBody User user){
        User savedUser = (User) userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
            ResponseEntity.created(location).build();
        return new ResponseEntity<>("User " + savedUser.getEmailId() + " created successfully ", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE,path= "/users/{id}")
    public ResponseEntity<Object> deleteAnUser(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        return new ResponseEntity<>("User " +  user.get().getEmailId() + " deleted successfully", HttpStatus.OK);
    }





}
