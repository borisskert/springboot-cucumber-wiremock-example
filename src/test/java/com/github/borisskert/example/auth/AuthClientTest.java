package com.github.borisskert.example.auth;

import com.github.borisskert.example.SpringBootWithWireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//@SpringBootTest
//@ActiveProfiles("IT")
//@AutoConfigureWireMock(port = 0)
@SpringBootWithWireMockTest
class AuthClientTest {

    @Autowired
    private AuthClient client;

    @Value("${wiremock.server.port}")
    private Short wireMockPort;

    @Autowired
    private AuthProperties authProperties;

    @BeforeEach
    public void setup() throws Exception {
        String url = MessageFormat.format("http://localhost:{0}/auth/token", wireMockPort.toString());
        authProperties.setUrl(url);
    }

    @Test
    public void shouldGetToken() throws Exception {
        String expectedToken = "auth_token_for_test";
        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(expectedToken))
        );

        String token = client.getToken();

        assertThat(token, is(equalTo(expectedToken)));
    }
}
