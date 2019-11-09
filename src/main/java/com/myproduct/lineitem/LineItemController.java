package com.myproduct.lineitem;

import com.myproduct.orders.OrderController;
import com.myproduct.orders.Orders;
import com.myproduct.products.ProductRepository;
import com.myproduct.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.Optional;

@RestController
public class LineItemController {

    @Autowired
    private LineItemRepository lineItemRepository;


    @RequestMapping(method = RequestMethod.GET,path= "/lineitem/{id}")
    public LineItem getALineItem(@PathVariable Integer id){
        Optional<LineItem> lineItem = lineItemRepository.findById(id);
        return lineItem.get();
    }

    @RequestMapping(method = RequestMethod.GET,path= "/lineitems")
    public List<LineItem> getAllLineItems(){
        return lineItemRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST,path= "/lineitems")
    public ResponseEntity<Object> createALineItems(@RequestBody LineItem lineItem){
        LineItem savedItem = (LineItem) lineItemRepository.save(lineItem);
        return new ResponseEntity<>("User " + savedItem.getId() + " created successfully ", HttpStatus.CREATED);
    }
}
