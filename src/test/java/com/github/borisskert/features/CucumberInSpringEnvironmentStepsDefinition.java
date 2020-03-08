package com.github.borisskert.features;

import com.github.borisskert.WireMockConfiguration;
import com.github.borisskert.example.Application;
import io.cucumber.java.en.Given;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("IT")
@ContextConfiguration(
        classes = {
                Application.class,
                WireMockConfiguration.class
        }
)
@ComponentScan(basePackages = "com.github.borisskert.features")
public class CucumberInSpringEnvironmentStepsDefinition {

    @Given("our spring application is running")
    public void theRemoteDataSourceDeliversNoProducts() {
    }
}
