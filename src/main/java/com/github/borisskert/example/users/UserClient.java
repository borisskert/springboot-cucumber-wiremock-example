package com.github.borisskert.example.users;

import com.github.borisskert.example.auth.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final UserProperties properties;
    private final AuthClient authClient;

    @Autowired
    public UserClient(RestTemplate restTemplate, UserProperties properties, AuthClient authClient) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.authClient = authClient;
    }

    public User[] getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authClient.getToken());

        ResponseEntity<User[]> response = restTemplate.exchange(
                properties.getUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                User[].class
        );

        return response.getBody();
    }
}
