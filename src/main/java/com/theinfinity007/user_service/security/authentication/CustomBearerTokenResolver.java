package com.theinfinity007.user_service.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.List;

public class CustomBearerTokenResolver implements BearerTokenResolver {
    private final String matchingPatternAttributeName = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";

    private RequestMappingHandlerMapping handlerMapping;
    private HashSet<String> jwtAccessRoutes;
    private DefaultBearerTokenResolver defaultBearerTokenResolver = new DefaultBearerTokenResolver();

    public CustomBearerTokenResolver(String[] jwtAccessRoutes, RequestMappingHandlerMapping handlerMapping) {
        this.jwtAccessRoutes = new HashSet<>(List.of(jwtAccessRoutes));
        this.handlerMapping = handlerMapping;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        try {
            HandlerExecutionChain _handler = handlerMapping.getHandler(request);    // Resolve the handler
            Object routeTemplatePatternObj = request.getAttribute(matchingPatternAttributeName);

            if (routeTemplatePatternObj == null) {
                return null;
            }

            String routeTemplatePattern = routeTemplatePatternObj.toString();
            String method = request.getMethod();

            String routePattern = method + ":" + routeTemplatePattern;

            // Early return if the route is not part of jwt access routes
            if (!jwtAccessRoutes.contains(routePattern)) {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Return the authorization
        return defaultBearerTokenResolver.resolve(request);
    }
}
