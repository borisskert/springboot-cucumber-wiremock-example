package com.github.borisskert.features.models;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Objects;

public class User {
    public static final TypeReference<List<User>> LIST_TYPE = new TypeReference<List<User>>() {
    };

    public final String id;
    public final String username;
    public final String email;
    public final String name;

    public User(String id, String username, String email, String name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
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
