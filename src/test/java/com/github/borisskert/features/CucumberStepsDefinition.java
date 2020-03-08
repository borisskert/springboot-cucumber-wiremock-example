package com.github.borisskert.features;

import com.github.borisskert.WireMockConfiguration;
import com.github.borisskert.example.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
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
public @interface CucumberStepsDefinition {
}
