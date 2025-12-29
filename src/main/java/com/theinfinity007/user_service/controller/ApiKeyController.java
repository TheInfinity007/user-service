package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.request.ApiKeyCreateRequestDto;
import com.theinfinity007.user_service.entity.ApiKey;
import com.theinfinity007.user_service.mapper.request.ApiKeyRequestMapper;
import com.theinfinity007.user_service.service.ApiKeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Api Key")
@RestController
@RequestMapping("/v1/apikeys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyRequestMapper apiKeyRequestMapper;
    private final ApiKeyService apiKeyService;

    @PostMapping("")
    public ResponseEntity<ApiKey> createApiKey(@RequestBody @Valid ApiKeyCreateRequestDto apiKeyCreateRequestDto) {
        ApiKey apiKey = apiKeyRequestMapper.populate(apiKeyCreateRequestDto);

        apiKey = apiKeyService.create(apiKey);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiKey);
    }
}
