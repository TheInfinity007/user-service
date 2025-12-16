package com.theinfinity007.user_service.constant;

public class DatabaseConstant {
    // Table names
    public static final String TABLE_API_KEY = "apiKeys";

    // Index Names
    public static final String INDEX_API_KEY_UNIQUE = "idx_api_key_unique";

    // Column Names
    public static final String COLUMN_NAME_API_KEY = "api_key";

    private DatabaseConstant() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
