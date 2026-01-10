package com.theinfinity007.user_service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class HttpClientImpl implements HttpClient {

    RestClient client;

    public HttpClientImpl(RestClient.Builder builder) {
        this.client = builder.build();
    }

    @Override
    public <B> ResponseEntity<B> request(
            HttpMethod httpMethod, String uri, Object body,
            Map<String, String> headers,
            Map<String, String> pathVariables,
            Map<String, String> queryParams,
            Class<B> responseType) {

        RestClient.RequestBodySpec requestSpec = client.method(httpMethod)
                .uri(uri, uriBuilder -> {

                    if (queryParams != null) {
                        queryParams.forEach(uriBuilder::queryParam);
                    }

                    return pathVariables != null ? uriBuilder.build(pathVariables) : uriBuilder.build();
                });

        if (body != null) {
            requestSpec.body(body);
        }

        if (headers != null && !headers.isEmpty()) {
            requestSpec.headers(h -> headers.forEach(h::add));
        }

        return requestSpec.retrieve().toEntity(responseType);
    }
}
