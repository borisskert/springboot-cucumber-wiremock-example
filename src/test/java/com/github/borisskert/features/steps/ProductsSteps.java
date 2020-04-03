package com.github.borisskert.features.steps;

import com.github.borisskert.features.models.Product;
import com.github.borisskert.features.models.ProductToCreate;
import com.github.borisskert.features.world.CucumberHttpClient;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ProductsSteps {

    @Autowired
    private CucumberHttpClient httpClient;

    @When("I ask for all products")
    public void iAskForAllProducts() {
        httpClient.get("/api/v1/products");
    }

    @Then("should return no products")
    public void shouldReturnNoProducts() {
        httpClient.verifyLatestStatus(HttpStatus.OK);
        httpClient.verifyLatestBodyIsEmptyArray();
    }

    @Then("I should get following products")
    public void iShouldGetFollowingProducts(List<Product> dataTable) {
        httpClient.verifyLatestStatus(HttpStatus.OK);
        httpClient.verifyLatestBodyIsEqualTo(dataTable, Product.LIST_TYPE);
    }

    @When("I request a product creation")
    public void iRequestAProductCreation(ProductToCreate dataTable) {
        httpClient.post("/api/v1/products", dataTable);
    }

    @And("I should get the created product ID {string}")
    public void iShouldGetTheCreatedProductID(String expectedProductId) {
        httpClient.verifyLatestStatus(HttpStatus.CREATED);
        httpClient.verifyLatestBodyIsEqualTo(expectedProductId);
    }

    @DataTableType
    public Product defineProduct(Map<String, String> entry) {
        return Product.from(entry);
    }

    @DataTableType
    public ProductToCreate defineProductToCreate(Map<String, String> entry) {
        return ProductToCreate.from(entry);
    }
}
