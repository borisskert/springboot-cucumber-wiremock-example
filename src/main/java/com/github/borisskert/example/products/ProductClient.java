package com.github.borisskert.example.products;

import com.github.borisskert.example.auth.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductProperties properties;

    @Autowired
    private AuthClient authClient;

    public Product[] getProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authClient.getToken());

        ResponseEntity<Product[]> response = restTemplate.exchange(
                properties.getUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Product[].class
        );

        return response.getBody();
    }
}
