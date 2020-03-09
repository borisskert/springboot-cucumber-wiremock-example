package com.github.borisskert.example.products;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductToCreate {

    private final String name;
    private final Double price;

    public ProductToCreate(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
