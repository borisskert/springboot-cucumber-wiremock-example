package com.github.borisskert.features.steps;

import com.github.borisskert.features.models.Product;
import com.github.borisskert.features.models.ProductToCreate;
import com.github.borisskert.features.models.User;
import com.github.borisskert.features.world.Authentication;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.borisskert.features.utils.WireMockUtils.createdForJson;
import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.common.Json.write;

/**
 * Contains the all WireMock steps for this solution. It's possible to split this class.
 */
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
    public void theRemoteDataSourceDeliversFollowingProducts(List<Product> dataTable) {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/products")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okForJson(dataTable))
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

    @And("the remote data source will not deliver a user for id {string}")
    public void theRemoteDataSourceWillNotDeliverAUserForId(String userId) {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                get("/users/" + userId)
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
                        .willReturn(notFound())
        );
    }

    @Then("the remote data source should be requested to create the product")
    public void theRemoteDataSourceShouldBeRequestedToCreateTheProduct(ProductToCreate dataTable) {
        String validToken = authentication.verifyAndGetAuthToken();

        verify(
                1,
                postRequestedFor(urlEqualTo("/products"))
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
                        .withRequestBody(equalToJson(write(dataTable)))
        );
    }

    @And("the remote data source should return the created product")
    public void theRemoteDataSourceShouldReturnTheCreatedProduct(Product dataTable) {
        String validToken = authentication.verifyAndGetAuthToken();

        stubFor(
                post("/products")
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
                        .willReturn(createdForJson(dataTable))
        );
    }
}
