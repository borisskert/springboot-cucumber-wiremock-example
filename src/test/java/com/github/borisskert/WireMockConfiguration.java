package com.github.borisskert;

import com.github.borisskert.example.auth.AuthProperties;
import com.github.borisskert.example.products.ProductProperties;
import com.github.borisskert.example.users.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

@Configuration
public class WireMockConfiguration {

    @Value("${wiremock.server.port}")
    private Short wireMockPort;

    @Autowired
    private ProductProperties productProperties;

    @Autowired
    private UserProperties userProperties;

    @Autowired
    private AuthProperties authProperties;

    @PostConstruct
    public void setup() throws Exception {
        setupProductProperties();
        setupUserProperties();
        setupAuthProperties();
    }

    private void setupAuthProperties() {
        String url = MessageFormat.format("http://localhost:{0}/auth/token", wireMockPort.toString());
        authProperties.setUrl(url);
    }

    private void setupUserProperties() {
        String url = MessageFormat.format("http://localhost:{0}/users", wireMockPort.toString());
        userProperties.setUrl(url);
    }

    private void setupProductProperties() {
        String url = MessageFormat.format("http://localhost:{0}/products", wireMockPort.toString());
        productProperties.setUrl(url);
    }
}
