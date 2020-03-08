package com.github.borisskert.example;

import com.github.borisskert.features.CucumberInSpringEnvironmentStepsDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = CucumberInSpringEnvironmentStepsDefinition.class
                )
        }
)
@ActiveProfiles("IT")
public class TestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
