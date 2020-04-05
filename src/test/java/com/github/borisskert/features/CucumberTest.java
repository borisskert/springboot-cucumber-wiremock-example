package com.github.borisskert.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * The cucumber test entry point
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "classpath:features",
        plugin = {"pretty", "html:target/cucumber"}
)
public class CucumberTest {
    // you should leave this class empty.
}
