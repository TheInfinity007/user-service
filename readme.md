# User Service

Todos
* Create API to fetch the user details using the access token.

## Bugs
* Getting expired token error in refresh token and revoke token api endpoints
* Handle the empty spaces by trimming the strings from payload

## High Priority (Core User APIs)
* GET /v1/users/me - Get current user profile using access token
* PUT /v1/users/me - Update current user profile
* PUT /v1/users/me/password - Change password
* DELETE /v1/users/me - Delete user account
* API Key Authentication

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


2. Email Verification Flow
POST /v1/users/send-verification
Sends/resends email verification link.
Request:
{
  "email": "user@example.com"
}
Use Cases:
New user registration (automatic)
User manually requests resend
Email changed, needs re-verification
Process:
Check if user exists
Check if already verified (return early if yes)
Generate verification token
Store token with user ID and expiration
Send email with verification link: https://yourapp.com/verify-email?token=abc789
Response:
{
  "message": "Verification email sent"
}
<hr></hr>
POST /v1/users/verify-email
Verifies email address using the token.
Request:
{
  "token": "abc789"
}
Process:
Validate token exists and hasn't expired
Find associated user
Update user's emailVerified status to true
Update emailVerifiedAt timestamp
Delete/invalidate token
Optionally: Grant additional permissions (if you restrict unverified users)
Response:
{
  "message": "Email successfully verified"
}
<hr></hr>


PasswordResetToken
AccountRecoveryService