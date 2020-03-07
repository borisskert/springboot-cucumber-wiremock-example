package com.github.borisskert.example.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ActiveProfiles("IT")
class AuthPropertiesTest {

    @Autowired
    private AuthProperties properties;

    @Test
    public void shouldProvideClientId() throws Exception {
        assertThat(properties.getClientId(), is(equalTo("my_client_id_for_test")));
    }

    @Test
    public void shouldProvideClientSecret() throws Exception {
        assertThat(properties.getClientSecret(), is(equalTo("my_client_secret_for_test")));
    }

    @Test
    public void shouldProvideUrl() throws Exception {
        assertThat(properties.getUrl(), is(equalTo("https://my-auth-server-for-test/auth/token")));
    }
}
