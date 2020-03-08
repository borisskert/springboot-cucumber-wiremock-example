package com.github.borisskert;

import com.github.borisskert.example.auth.AuthProperties;
import com.github.borisskert.example.products.ProductProperties;
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
    private ProductProperties properties;

    @Autowired
    private AuthProperties authProperties;

    @PostConstruct
    public void setup() throws Exception {
        String url = MessageFormat.format("http://localhost:{0}/products", wireMockPort.toString());
        properties.setUrl(url);

        url = MessageFormat.format("http://localhost:{0}/auth/token", wireMockPort.toString());
        authProperties.setUrl(url);
    }
}
