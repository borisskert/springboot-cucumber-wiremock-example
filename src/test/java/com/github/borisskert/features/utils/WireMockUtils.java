package com.github.borisskert.features.utils;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.Json;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition;
import static java.net.HttpURLConnection.HTTP_CREATED;

public class WireMockUtils {

    /**
     * Prevent public creation
     */
    private WireMockUtils() {
        throw new IllegalStateException("No instance allowed");
    }

    public static ResponseDefinitionBuilder createdForJson(Object body) {
        return responseDefinition().withStatus(HTTP_CREATED)
                .withBody(Json.write(body))
                .withHeader("Content-Type", "application/json");
    }
}
