package com.github.borisskert.example.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UserClient userClient;

    @Autowired
    public UsersController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public User[] getProducts() {
        return userClient.getUsers();
    }
}
