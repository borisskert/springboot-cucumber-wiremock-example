package com.github.borisskert.features.world;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Wrapper class to verify http calls. May be extended with other HttpMethod calls.
 *
 * @param <T> body class type
 */
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@SuppressWarnings("rawtypes")
public class CucumberHttpClient<T> {

    private final TestRestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final MultiValueMap<String, String> headers = new HttpHeaders();

    private ResponseEntity<String> lastResponse;

    @Autowired
    public CucumberHttpClient(TestRestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public void get(String url, Object... urlVariables) {
        requestEmptyBody(HttpMethod.GET, url, urlVariables);
    }

    public void post(String url, Object body, Object... urlVariables) {
        request(HttpMethod.POST, url, body, urlVariables);
    }

    public void postEmptyBody(String url, Object... urlVariables) {
        requestEmptyBody(HttpMethod.POST, url, urlVariables);
    }

    public void verifyLatestBody(List<T> expectedBody, TypeReference<List<T>> type) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            List<T> convertedBody = tryToConvertFromJson(body, type);

            assertThat(convertedBody, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestBody(T expectedBody, Class<T> type) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            T convertedBody = tryToConvertFromJson(body, type);

            assertThat(convertedBody, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
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
            ResponseEntity response = maybeResponse.get();
            assertThat(response.getStatusCode(), is(equalTo(expectedStatus)));
        } else {
            fail("Got no response");
        }
    }

    private Optional<ResponseEntity<String>> getLastResponse() {
        return Optional.ofNullable(lastResponse);
    }

    private void requestEmptyBody(HttpMethod method, String url, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                method,
                new HttpEntity<>(headers),
                String.class,
                urlVariables
        );

        headers.clear();
    }

    private void request(HttpMethod method, String url, Object body, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                method,
                new HttpEntity<>(body, headers),
                String.class,
                urlVariables
        );

        headers.clear();
    }

    private T tryToConvertFromJson(String body, Class<T> type) {
        T convertedBody;

        try {
            convertedBody = mapper.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return convertedBody;
    }

    private List<T> tryToConvertFromJson(String body, TypeReference<List<T>> type) {
        List<T> convertedBody;

        try {
            convertedBody = mapper.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return convertedBody;
    }
}
