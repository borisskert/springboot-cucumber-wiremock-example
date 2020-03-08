package com.github.borisskert.features.client;

import com.github.borisskert.features.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductTestClient {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<List<Product>> lastResponse;

    public void requestProducts() {
        lastResponse = restTemplate.exchange(
                "/api/v1/products",
                HttpMethod.GET,
                null,
                Product.LIST_TYPE
        );
    }

    public Optional<ResponseEntity<List<Product>>> getLastResponse() {
        return Optional.ofNullable(lastResponse);
    }

}
