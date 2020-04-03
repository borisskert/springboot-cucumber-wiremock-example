package com.github.borisskert.features.world;

import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToIgnoreCase;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@Component
public class Authentication {

    private String authToken;

    public void simulateLogin() {
        String validToken = "my_valid_token";

        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(validToken))
        );

        authToken = validToken;
    }

    public String verifyAndGetAuthToken() {
        assertThat(authToken, is(not(nullValue())));
        return authToken;
    }
}
