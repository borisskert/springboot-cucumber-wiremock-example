package com.github.borisskert.example.products;

import com.github.borisskert.example.SpringBootWithWireMockTest;
import com.github.borisskert.example.auth.AuthProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToIgnoreCase;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//@SpringBootTest
//@ActiveProfiles("IT")
//@AutoConfigureWireMock(port = 0)
////@ContextConfiguration(classes = TestConfiguration.class)
@SpringBootWithWireMockTest
class ProductClientTest {

    @Autowired
    private ProductClient productClient;

    @Value("${wiremock.server.port}")
    private Short wireMockPort;

    @Autowired
    private ProductProperties properties;

    @Autowired
    private AuthProperties authProperties;

    @BeforeEach
    public void setup() throws Exception {
        String url = MessageFormat.format("http://localhost:{0}/products", wireMockPort.toString());
        properties.setUrl(url);

        url = MessageFormat.format("http://localhost:{0}/auth/token", wireMockPort.toString());
        authProperties.setUrl(url);
    }

    @Test
    public void shouldRetrieveProductsWithValidToken() throws Exception {
        String validToken = "my_valid_token";

        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(validToken))
        );

        stubFor(
                get("/products")
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
                        .willReturn(okJson("[" +
                                "{" +
                                "\"id\": \"1\"," +
                                "\"name\": \"Honey\"," +
                                "\"price\": 1.23" +
                                "}," +
                                "{" +
                                "\"id\": \"2\"," +
                                "\"name\": \"Bread\"," +
                                "\"price\": 2.34" +
                                "}," +
                                "{" +
                                "\"id\": \"3\"," +
                                "\"name\": \"Beer\"," +
                                "\"price\": 3.45" +
                                "}" +
                                "]"))
        );

        Product[] products = productClient.getProducts();

        assertThat(products.length, is(equalTo(3)));
        assertThat(products[0], is(equalTo(new Product("1", "Honey", 1.23))));
        assertThat(products[1], is(equalTo(new Product("2", "Bread", 2.34))));
        assertThat(products[2], is(equalTo(new Product("3", "Beer", 3.45))));
    }
}