package com.github.borisskert.example.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ActiveProfiles("IT")
class ProductPropertiesTest {

    @Autowired
    private ProductProperties properties;

    @Test
    public void shouldProvideUrl() throws Exception {
        assertThat(properties.getUrl(), is(equalTo("https://my-products-server-for-test/products")));
    }
}
