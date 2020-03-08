package com.github.borisskert.features.steps;

import com.github.borisskert.features.CucumberStepsDefinition;
import com.github.borisskert.features.client.ProductTestClient;
import com.github.borisskert.features.models.Product;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpStatus.OK;

@CucumberStepsDefinition
public class ProductsSteps {

    @Autowired
    private ProductTestClient client;

    @Given("the remote data source delivers no products")
    public void theRemoteDataSourceDeliversNoProducts() {
        String validToken = "my_valid_token";

        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(validToken))
        );

        stubFor(
                get("/products")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okJson("[]"))
        );
    }

    @When("I ask for all products")
    public void iAskForAllProducts() {
        client.requestProducts();
    }

    @Then("should return no products")
    public void shouldReturnNoProducts() {
        Optional<ResponseEntity<List<Product>>> maybeResponse = client.getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<List<Product>> response = maybeResponse.get();
            assertThat(response.getStatusCode(), is(equalTo(OK)));
            assertThat(response.getBody(), is(equalTo(new ArrayList<>())));
        } else {
            fail("Got no response");
        }
    }

    @Given("the remote data source delivers following products")
    public void theRemoteDataSourceDeliversFollowingProducts(List<Product> table) {
        String validToken = "my_valid_token";

        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(validToken))
        );

        stubFor(
                get("/products")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okForJson(table))
        );
    }

    @Then("I should get following products")
    public void iShouldGetFollowingProducts(List<Product> table) {
        Optional<ResponseEntity<List<Product>>> maybeResponse = client.getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<List<Product>> response = maybeResponse.get();
            assertThat(response.getStatusCode(), is(equalTo(OK)));
            assertThat(response.getBody(), is(equalTo(table)));
        } else {
            fail("Got no response");
        }
    }

    @DataTableType
    public Product defineProduct(Map<String, String> entry) {
        String id = entry.get("ID");
        String name = entry.get("Name");
        Double price = Double.valueOf(entry.get("Price"));

        return new Product(id, name, price);
    }
}
