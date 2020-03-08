package com.github.borisskert.example.users;

import com.github.borisskert.example.auth.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

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

    public Optional<User> getUser(String id) {
        Optional<User> maybeUser;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authClient.getToken());

        try {
            ResponseEntity<User> response = restTemplate.exchange(
                    properties.getUrl() + "/" + id,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    User.class
            );

            maybeUser = Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException.NotFound notFound) {
            maybeUser = Optional.empty();
        }

        return maybeUser;
    }
}
