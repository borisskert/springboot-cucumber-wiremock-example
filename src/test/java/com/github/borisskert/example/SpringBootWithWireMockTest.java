package com.github.borisskert.example;

import com.github.borisskert.TestConfiguration;
import com.github.borisskert.WireMockConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@ActiveProfiles("IT")
@AutoConfigureWireMock(port = 0)
@ContextConfiguration(
        classes = {
                TestConfiguration.class,
                WireMockConfiguration.class
        }
)
public @interface SpringBootWithWireMockTest {
}
