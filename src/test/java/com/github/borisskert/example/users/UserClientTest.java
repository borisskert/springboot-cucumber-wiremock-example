package com.github.borisskert.example.users;

import com.github.borisskert.example.SpringBootWithWireMockTest;
import com.github.borisskert.example.auth.AuthProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;
import java.util.Optional;

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
//@AutoConfigureWireMock(port = 0)
//@ActiveProfiles("IT")
@SpringBootWithWireMockTest
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
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
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

    @Test
    public void shouldRetrieveOneUserById() throws Exception {
        String validToken = "my_valid_token";

        stubFor(
                post("/auth/token")
                        .withHeader("client-id", equalToIgnoreCase("my_client_id_for_test"))
                        .withHeader("client-secret", equalToIgnoreCase("my_client_secret_for_test"))
                        .willReturn(ok(validToken))
        );

        stubFor(
                get("/users/123")
                        .withHeader("Authorization", equalTo("Bearer " + validToken))
                        .willReturn(okJson("{" +
                                "\"id\": \"123\"," +
                                "\"username\": \"johnair\"," +
                                "\"email\": \"john.air@fakemail.yu\"," +
                                "\"name\": \"John Air\"" +
                                "}"
                        ))
        );

        Optional<User> maybeUser = client.getUser("123");

        assertThat(maybeUser.isPresent(), is(true));
        assertThat(maybeUser.get(), is(equalTo(new User("123", "johnair", "john.air@fakemail.yu", "John Air"))));
    }
}