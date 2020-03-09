package com.github.borisskert.example.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    }

    @PostMapping
    public ResponseEntity<String> getProducts(@RequestBody ProductToCreate productToCreate) {
        String createdProductId = productClient.createProduct(productToCreate);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdProductId);

    }
}
