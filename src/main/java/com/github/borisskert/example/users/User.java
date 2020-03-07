package com.github.borisskert.example.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a user which is immutable
 */
public class User {

    private final String id;
    private final String username;
    private final String email;
    private final String name;

    public User(
            @JsonProperty("id") String id,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
