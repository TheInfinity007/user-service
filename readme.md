# User Service

Todos
* Create API to fetch the user details using the access token.

## High Priority (Core User APIs)
* GET /v1/users/me - Get current user profile using access token
* PUT /v1/users/me - Update current user profile
* PUT /v1/users/me/password - Change password
* DELETE /v1/users/me - Delete user account

## Medium Priority (Account Recovery)
* POST /v1/users/forgot-password - Initiate password reset
* POST /v1/users/reset-password - Reset password with token
* POST /v1/users/send-verification - Send email verification
* POST /v1/users/verify-email - Verify email with token
## Medium Priority (Session Management)
* GET /v1/users/me/sessions - Get active user sessions
* DELETE /v1/users/me/sessions/{sessionId} - Revoke specific session
* DELETE /v1/users/me/sessions - Revoke all sessions


## High Priority (Core User APIs)
* GET /v1/users/me - Get current user profile using access token
* PUT /v1/users/me - Update current user profile
* PUT /v1/users/me/password - Change password
* DELETE /v1/users/me - Delete user account

## Medium Priority (Account Recovery)
* POST /v1/users/forgot-password - Initiate password reset
* POST /v1/users/reset-password - Reset password with token
* POST /v1/users/send-verification - Send email verification
* POST /v1/users/verify-email - Verify email with token

## Medium Priority (Session Management)
* GET /v1/users/me/sessions - Get active user sessions
* DELETE /v1/users/me/sessions/{sessionId} - Revoke specific session
* DELETE /v1/users/me/sessions - Revoke all sessions


Authentication Manager

Provider manager -> common implementation of Authentication Manager
    ProviderManager delegates to a List of AuthenticationProvider instances

Authentication Provider -Perform a specific type of authentiction, for example, DAOAuthenticationProvider supports username/password based authentication, while
JwtAuthenticationProvider supports authenticating a JWT token