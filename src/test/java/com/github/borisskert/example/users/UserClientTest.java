package com.github.borisskert.example.users;

import com.github.borisskert.example.auth.AuthProperties;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.text.MessageFormat;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("IT")
class UserClientTest {

    @Autowired
    private UserClient client;

    @Value("${wiremock.server.port}")
    private Short wireMockPort;

    @Autowired
    private UserProperties properties;

    @Autowired
    private AuthProperties authProperties;

    @BeforeEach
    public void setup() throws Exception {
        String url = MessageFormat.format("http://localhost:{0}/users", wireMockPort.toString());
        properties.setUrl(url);

        url = MessageFormat.format("http://localhost:{0}/auth/token", wireMockPort.toString());
        authProperties.setUrl(url);
    }

    @Test
    public void shouldRetrieveUsers() throws Exception {
        String validToken = "my_valid_token";

        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(validToken))
        );

        stubFor(
                get("/users")
                        .withHeader("Authorization", WireMock.equalTo("Bearer " + validToken))
                        .willReturn(okJson("[" +
                                "{" +
                                "\"id\": \"1\"," +
                                "\"username\": \"smitj\"," +
                                "\"email\": \"john.smith@fakemail.com\"," +
                                "\"name\": \"John Smith\"" +
                                "}," + "{" +
                                "\"id\": \"2\"," +
                                "\"username\": \"mustermann\"," +
                                "\"email\": \"max.mustermann@fakemail.de\"," +
                                "\"name\": \"Max Mustermann\"" +
                                "}" +
                                "]"
                        ))
        );

        User[] users = client.getUsers();

        assertThat(users.length, is(equalTo(2)));
        assertThat(users[0], is(equalTo(new User("1", "smitj", "john.smith@fakemail.com", "John Smith"))));
        assertThat(users[1], is(equalTo(new User("2", "mustermann", "max.mustermann@fakemail.de", "Max Mustermann"))));
    }
}