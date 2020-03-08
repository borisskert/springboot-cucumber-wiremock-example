package com.github.borisskert.example.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final ProductClient productClient;

    @Autowired
    public ProductsController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping
    public Product[] getProducts() {
        return productClient.getProducts();
//        return new Product[] {
//                new Product("1", "Honey", 1.23),
//                new Product("2", "Bread", 2.34),
//                new Product("3", "Beer", 3.45)
//        };
    }
}
