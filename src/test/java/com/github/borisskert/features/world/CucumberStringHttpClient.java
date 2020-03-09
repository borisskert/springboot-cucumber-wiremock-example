package com.github.borisskert.features.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Wrapper class to verify string http calls. May be extended with other HttpMethod calls.
 */
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class CucumberStringHttpClient {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> lastResponse;

    public void requestGet(String url) {
        lastResponse = restTemplate.getForEntity(
                url,
                String.class
        );
    }

    public void requestPost(String url, Object body, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body),
                String.class,
                urlVariables
        );
    }

    public void requestPost(String url, Object body, MultiValueMap<String, String> headers, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class,
                urlVariables
        );
    }

    public void requestPost(String url, MultiValueMap<String, String> headers, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class,
                urlVariables
        );
    }

    public void verifyLatestBody(String expectedBody) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            assertThat(body, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestStatus(HttpStatus expectedStatus) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            assertThat(response.getStatusCode(), is(equalTo(expectedStatus)));
        } else {
            fail("Got no response");
        }
    }

    private Optional<ResponseEntity<String>> getLastResponse() {
        return Optional.ofNullable(lastResponse);
    }
}
