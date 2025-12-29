package com.theinfinity007.user_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

import static com.theinfinity007.user_service.constant.AppConstant.AUTH_STRATEGY_OAUTH;
import static com.theinfinity007.user_service.constant.AppConstant.AUTH_STRATEGY_API_KEY;
import static com.theinfinity007.user_service.constant.AppConstant.HEADER_X_API_KEY;


@Configuration
@OpenAPIDefinition(
        info = @Info(title = "User Service API", version = "1.0", description = "User management and authentication"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Url"),
                @Server(url = "https://userservice.theinfinity007.com", description = "Production")
        }
)
@SecuritySchemes({
        @SecurityScheme(type = SecuritySchemeType.HTTP, name = AUTH_STRATEGY_OAUTH, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER),
        @SecurityScheme(type = SecuritySchemeType.APIKEY, name = AUTH_STRATEGY_API_KEY, paramName = HEADER_X_API_KEY, in = SecuritySchemeIn.HEADER)
})
public class OpenApiConfig {
}
