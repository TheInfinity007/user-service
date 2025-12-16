package com.theinfinity007.user_service.mapper.request;

import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.dto.ApiKeyCreateRequestDto;
import com.theinfinity007.user_service.entity.ApiKey;
import com.theinfinity007.user_service.utils.CommonUtil;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyRequestMapper {
    public ApiKey populate(ApiKeyCreateRequestDto apiKeyCreateRequestDto) {
        ApiKey apiKey = new ApiKey();
        apiKey.setKey(CommonUtil.generateRandomString(AppConstant.API_KEY_LENGTH));
        apiKey.setDescription(apiKeyCreateRequestDto.getDescription());
        apiKey.setScopes(apiKeyCreateRequestDto.getScopes());
        apiKey.setExpiredAt(apiKeyCreateRequestDto.getExpiredAt());

        return apiKey;
    }
}
