package com.theinfinity007.user_service.constant;

public class AppConstant {
    public static final String APPLICATION_JSON = "application/json";
    public static final String HEADER_X_API_KEY = "x-api-key";
    public static final int API_KEY_LENGTH = 32;
    public static final String AUTH_STRATEGY_API_KEY = "api_key";
    public static final String AUTH_STRATEGY_OAUTH = "oauth";
    public static final String COMMUNICATION_CHANNEL_EMAIL = "email";
    public static final String TEMPLATE_TYPE_EMAIL = "EMAIL";

    private AppConstant(){} // private constructor so that no once can initialize even by mistake
}
