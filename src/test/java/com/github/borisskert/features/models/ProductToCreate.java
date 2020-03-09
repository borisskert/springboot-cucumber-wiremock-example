package com.github.borisskert.features.models;

import java.util.Map;

public class ProductToCreate {
    public final String name;
    public final Double price;

    public ProductToCreate(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public static ProductToCreate from(Map<String, String> entry) {
        String name = entry.get("Name");
        Double price = Double.valueOf(entry.get("Price"));

        return new ProductToCreate(name, price);
    }
}
