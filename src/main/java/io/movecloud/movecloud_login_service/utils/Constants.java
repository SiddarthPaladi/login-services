package io.movecloud.movecloud_login_service.utils;

public class Constants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String EXTRACTED_USERNAME_LOG = "Extracted username: {}";
    public static final String AUTHENTICATED_USER_LOG = "Authenticated user: {}";
    public static final String INVALID_TOKEN_LOG = "Invalid token for user: {}";
    public static final String ERROR_IN_JWT_FILTER_LOG = "Error in JwtFilter";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";
    public static final Integer BCRYPT_STRENGTH = 12;
    public static final String CONFIGURING_AUTH_PROVIDER_LOG = "Configuring AuthenticationProvider";
    public static final String CONFIGURING_SECURITY_FILTER_CHAIN_LOG = "Configuring SecurityFilterChain";
    public static final String CONFIGURING_AUTH_MANAGER_LOG = "Configuring AuthenticationManager";
    public static final String ERROR_CONFIGURING_SECURITY_FILTER_CHAIN_LOG = "Error configuring SecurityFilterChain";
    public static final String ERROR_CONFIGURING_AUTH_MANAGER_LOG = "Error configuring AuthenticationManager";
    public static final String SESSION_ID_HEADER = "Session-Id";
    public static final String INVALID_SESSION_MESSAGE = "Invalid or missing session ID";
    public static final String SESSION_ID_LOG = "Session ID: {}";
    public static final String INVALID_SESSION_LOG = "Invalid or missing session ID";
    public static final String ERROR_IN_SESSION_FILTER_LOG = "Error in SessionFilter";
    public static final String HELLO_PATH = "/hello";
    public static final String ABOUT_PATH = "/about";
    public static final String HELLO_MESSAGE = "Hello World";
    public static final String ABOUT_MESSAGE_PREFIX = "Movecloud";
    public static final String ERROR_IN_HELLO_CONTROLLER_LOG = "Error in HelloController";
    public static final String REQUEST_RECEIVED_LOG = "Request received for path: {}";
    public static final String RESPONSE_SENT_LOG = "Response sent: {}";
    public static final String LOGIN_ATTEMPT_LOG = "Login attempt for user: {}";
    public static final String LOGIN_SUCCESS_LOG = "Login successful for user: {}";
    public static final String LOGIN_FAILED_LOG = "Login failed for user: {}";
    public static final String LOGIN_FAILED_MESSAGE = "Login Failed";
    public static final String ERROR_IN_USER_CONTROLLER_LOG = "Error in UserController";
    public static final String TOKEN_KEY = "token";
    public static final String SESSION_ID_KEY = "sessionId";
    public static final String MESSAGE_KEY = "message";
    public static final String ERROR_GENERATING_KEY_LOG = "Error generating Key:";
    public static final String TOKEN_GENERATED_LOG = "Token generated: {}";
    public static final String EXTRACTING_USERNAME_LOG = "Extracting username from token: {}";
    public static final String VALIDATING_TOKEN_LOG = "Validating token for user: {}";
    public static final String TOKEN_EXPIRED_LOG = "Token expired: {}";
    public static final String SESSION_ID_GENERATED_LOG = "Session ID generated: {}";
    public static final String ORGANIZATION_NAME_KEY = "organizationName";
    public static final String HMAC_ALGORITHM = "HmacSHA256";
    public static final String LOADING_USER_LOG = "Trying to load user by username: {}";
    public static final String USER_NOT_FOUND_LOG = "User not found: {}";
    public static final String USER_FOUND_LOG = "User found: {}";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User not found";
    public static final long SESSION_EXPIRATION_TIME = 1000 * 60 * 10; // 10 minutes
    public static final String ERROR_CREATING_SESSION = "Error creating session";
    public static final String ERROR_REGISTERING_USER = "Error registering user";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String $_API_BASE_URL = "${api.base-url}";


}
