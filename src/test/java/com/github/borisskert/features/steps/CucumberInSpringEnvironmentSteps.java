package com.github.borisskert.features.steps;

import com.github.borisskert.WireMockConfiguration;
import com.github.borisskert.example.Application;
import com.github.borisskert.features.CucumberConfiguration;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * This class loads the Spring Boot context into the cucumber tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("IT")
@ContextConfiguration(
        classes = {
                Application.class,
                CucumberConfiguration.class,
                WireMockConfiguration.class
        }
)
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
