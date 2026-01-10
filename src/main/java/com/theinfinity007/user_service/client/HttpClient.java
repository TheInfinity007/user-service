package com.theinfinity007.user_service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface HttpClient {
    <B> ResponseEntity<B> request(HttpMethod httpMethod, String uri, Object body, Map<String, String> headers, Map<String, String> pathVariables, Map<String, String> queryParams, Class<B> responseType);
}
