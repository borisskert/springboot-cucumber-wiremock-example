package com.github.borisskert.features.world;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    private ResponseEntity lastResponse;

    public void requestGetAsJson(String url) {
        lastResponse = restTemplate.getForEntity(
                url,
                JsonNode.class
        );
    }

    public void requestGet(String url) {
        lastResponse = restTemplate.getForEntity(
                url,
                String.class
        );
    }

    public void requestPostAsJson(String url, Object body, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body),
                JsonNode.class,
                urlVariables
        );
    }

    public void requestPostAsJson(String url, Object body, MultiValueMap<String, String> headers, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                JsonNode.class,
                urlVariables
        );
    }

    public void requestPostAsJson(String url, MultiValueMap<String, String> headers, Object... urlVariables) {
        lastResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                JsonNode.class,
                urlVariables
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

    public void verifyLatestBody(List<T> expectedBody, TypeReference<List<T>> type) {
        Optional<ResponseEntity> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<JsonNode> response = maybeResponse.get();
            JsonNode body = response.getBody();

            List<T> convertedBody = mapper.convertValue(body, type);

            assertThat(convertedBody, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestBody(T expectedBody, Class<T> type) {
        Optional<ResponseEntity> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<JsonNode> response = maybeResponse.get();
            JsonNode body = response.getBody();

            T convertedBody = mapper.convertValue(body, type);

            assertThat(convertedBody, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestBody(String expectedBody) {
        Optional<ResponseEntity> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity<String> response = maybeResponse.get();
            String body = response.getBody();

            assertThat(body, is(equalTo(expectedBody)));
        } else {
            fail("Got no response");
        }
    }

    public void verifyLatestStatus(HttpStatus expectedStatus) {
        Optional<ResponseEntity> maybeResponse = getLastResponse();

        if (maybeResponse.isPresent()) {
            ResponseEntity response = maybeResponse.get();
            assertThat(response.getStatusCode(), is(equalTo(expectedStatus)));
        } else {
            fail("Got no response");
        }
    }

    @SuppressWarnings("rawtypes")
    private Optional<ResponseEntity> getLastResponse() {
        return Optional.ofNullable(lastResponse);
    }
}
