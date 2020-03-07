package com.github.borisskert.example.client;

import com.github.borisskert.example.config.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {

    private final RestTemplate restTemplate;
    private final AuthProperties properties;

    @Autowired
    public AuthClient(RestTemplate restTemplate, AuthProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("client-id", properties.getClientId());
        headers.add("client-secret", properties.getClientSecret());

        ResponseEntity<String> response = restTemplate.postForEntity(
                properties.getUrl(),
                new HttpEntity<>(headers),
                String.class
        );

        return response.getBody();
    }
}
