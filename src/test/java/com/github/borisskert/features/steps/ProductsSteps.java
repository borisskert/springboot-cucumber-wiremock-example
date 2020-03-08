package com.github.borisskert.features.steps;

import com.github.borisskert.features.models.Product;
import com.github.borisskert.features.world.CucumberHttpClient;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductsSteps {

    @Autowired
    private CucumberHttpClient<Product> client;

    @When("I ask for all products")
    public void iAskForAllProducts() {
        client.requestGet("/api/v1/products");
    }

    @Then("should return no products")
    public void shouldReturnNoProducts() {
        client.verifyLatestStatus(HttpStatus.OK);
        client.verifyLatestBody(new ArrayList<>(), Product.LIST_TYPE);
    }

    @Then("I should get following products")
    public void iShouldGetFollowingProducts(List<Product> dataTable) {
        client.verifyLatestStatus(HttpStatus.OK);
        client.verifyLatestBody(dataTable, Product.LIST_TYPE);
    }

    @DataTableType
    public Product defineProduct(Map<String, String> entry) {
        String id = entry.get("ID");
        String name = entry.get("Name");
        Double price = Double.valueOf(entry.get("Price"));

        return new Product(id, name, price);
    }
}
