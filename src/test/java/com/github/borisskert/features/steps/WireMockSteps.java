package com.github.borisskert.features.steps;

import com.github.borisskert.features.models.Product;
import com.github.borisskert.features.models.User;
import com.github.borisskert.features.world.Authentication;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSteps {

    @Autowired
    private Authentication authentication;

    @Given("the remote data source delivers no products")
    public void theRemoteDataSourceDeliversNoProducts() {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/products")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okJson("[]"))
        );
    }

    @Given("the remote data source delivers following products")
    public void theRemoteDataSourceDeliversFollowingProducts(List<Product> table) {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/products")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okForJson(table))
        );
    }

    @And("the remote data source delivers no users")
    public void theRemoteDataSourceDeliversNoUsers() {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/users")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okJson("[]"))
        );
    }

    @And("the remote data source delivers following users")
    public void theRemoteDataSourceDeliversFollowingUsers(List<User> dataTable) {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/users")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okForJson(dataTable))
        );
    }

    @And("the remote data source delivers following user for id {string}")
    public void theRemoteDataSourceDeliversFollowingUserForId(String userId, User dataTable) {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/users/" + userId)
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
                        .willReturn(okForJson(dataTable))
        );
    }
}
