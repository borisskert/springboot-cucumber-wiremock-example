package com.github.borisskert.features.steps;

import com.github.borisskert.WireMockConfiguration;
import com.github.borisskert.example.Application;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * This class loads the Spring Boot context into the cucumber tests.
 */
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
public class CucumberInSpringEnvironmentSteps {

    @Autowired
    private ApplicationContext context;

    /**
     * This step is mandatory! Without this step cucumber will NOT load the Spring Boot context defined for this class.
     */
    @Given("our spring application is running")
    public void ourSpringApplicationIsRunning() {
        assertThat(context, is(not(nullValue())));
    }
}
