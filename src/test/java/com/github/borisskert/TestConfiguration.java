package com.github.borisskert;

import com.github.borisskert.features.steps.CucumberInSpringEnvironmentSteps;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = CucumberInSpringEnvironmentSteps.class
                )
        }
)
@ActiveProfiles("IT")
public class TestConfiguration {

}
