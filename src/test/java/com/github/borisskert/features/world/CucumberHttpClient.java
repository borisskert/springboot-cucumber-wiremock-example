package com.github.borisskert.features.world;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Wrapper class to verify http calls. May be extended with other HttpMethod calls.
 */
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class CucumberHttpClient {
    /* *****************************************************************************************************************
     * Constants
     **************************************************************************************************************** */

    private static final TypeReference<List<String>> STRING_LIST_TYPE = new TypeReference<List<String>>() {
    };

    /* *****************************************************************************************************************
     * Readonly fields
     **************************************************************************************************************** */

    private final TestRestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final MultiValueMap<String, String> headers = new HttpHeaders();

    /* *****************************************************************************************************************
     * Mutable fields
     **************************************************************************************************************** */

    private ResponseEntity<String> lastResponse;

    /* *****************************************************************************************************************
     * Constructor
     **************************************************************************************************************** */

    @Autowired
    public CucumberHttpClient(TestRestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    /* *****************************************************************************************************************
     * Methods to request
     **************************************************************************************************************** */

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

    public void put(String url, Object body, Object... urlVariables) {
        request(HttpMethod.PUT, url, body, urlVariables);
    }

    /* *****************************************************************************************************************
     * Methods to verify response
     **************************************************************************************************************** */

    public void verifyLatestStatus(HttpStatus expectedStatus) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            assertThat(response.getStatusCode(), is(equalTo(expectedStatus)));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestBodyIsEqualTo(String expectedBody) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            assertThat(body, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
    }

    public <T> void verifyLatestBodyIsEqualTo(T expectedBody, Class<T> type) {
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

    public <T> void verifyLatestBodyIsEqualTo(List<T> expectedBody, TypeReference<List<T>> type) {
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

    public <T> void verifyLatestBodyContainsInAnyOrder(List<T> expectedBody, TypeReference<List<T>> type) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            List<T> convertedBody = tryToConvertFromJson(body, type);

            assertThat(convertedBody, containsInAnyOrder(expectedBody.toArray()));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestBodyIsEmpty() {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            assertThat(body, is(equalTo("")));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestBodyIsEmptyArray() {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            List<String> convertedBody = tryToConvertFromJson(body, STRING_LIST_TYPE);

            assertThat(convertedBody, is(empty()));
        } else {
            fail("Got no response");
        }
    }

    public String getLatestResponseHeaderParam(String key) {
        Optional<ResponseEntity<String>> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            return response.getHeaders().getFirst(key);
        } else {
            throw new AssertionFailedError("Got no response");
        }
    }

    /* *****************************************************************************************************************
     * Private methods
     **************************************************************************************************************** */

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

    private <T> T tryToConvertFromJson(String body, Class<T> type) {
        T convertedBody;

        try {
            convertedBody = mapper.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return convertedBody;
    }

    private <T> List<T> tryToConvertFromJson(String body, TypeReference<List<T>> type) {
        List<T> convertedBody;

        try {
            convertedBody = mapper.readValue(body, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return convertedBody;
    }
}
