package com.github.borisskert.features;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.github.borisskert.features.world")
public class CucumberConfiguration {
}
