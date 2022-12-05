package com.example.shopproject.shop.controller;

import com.example.shopproject.shop.model.Product;
import com.example.shopproject.shop.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/save")
    public Product saveProduct(@RequestBody Product product) {
      return  productService.save(product);
    }

    @GetMapping("/getById/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.findById(id);
    }

    @GetMapping("/getByName/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product updateShop(@RequestBody Product product, @PathVariable Long id) {
        return productService.update(product, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.delete(id);
    }
}
