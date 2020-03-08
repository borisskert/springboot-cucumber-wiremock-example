package com.github.borisskert.features.models;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Product in cucumber context only.
 * You should not use the classes of your production because you can change your tests before changing your production code.
 */
public final class Product {
    public static final TypeReference<List<Product>> LIST_TYPE = new TypeReference<List<Product>>() {
    };

    public final String id;
    public final String name;
    public final Double price;

    public Product(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
