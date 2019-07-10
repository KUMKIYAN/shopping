package com.myproduct.products;

import com.myproduct.users.User;
import com.myproduct.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    public ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET,path= "/products")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET,path= "/products/{id}")
    public Product getAProductDetails(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }

    @RequestMapping(method = RequestMethod.POST,path= "/products")
    public ResponseEntity<Product> addAProduct(@Valid @RequestBody Product product){
        Product savedProduct = productRepository.save(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.GET,path= "/products/{id}/price")
    public Float getProductPrice(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        return product.get().getPrice();
    }

}
