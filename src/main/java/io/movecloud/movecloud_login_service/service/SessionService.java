package io.movecloud.movecloud_login_service.service;

import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service for handling session operations such as creation, validation, and invalidation.
 *
 * <p>\@author Dushyanth \@since 2024.1.6
 */
@Service
public class SessionService {
    private Map<String, Long> sessionStore = new HashMap<>();

    /**
     * Creates a new session for the given username.
     *
     * @param username the username for which the session is created
     * @return the generated session ID
     */
    public String createSession(String username) {
        try {
            String sessionId = UUID.randomUUID().toString();
            sessionStore.put(sessionId, System.currentTimeMillis() + Constants.SESSION_EXPIRATION_TIME);
            return sessionId;
        } catch (Exception e) {
            throw new MoveCloudException(Constants.ERROR_CREATING_SESSION);
        }
    }

    /**
     * Checks if the given session ID is valid.
     *
     * @param sessionId the session ID to check
     * @return true if the session is valid, false otherwise
     */
    public boolean isSessionValid(String sessionId) {
        Long expirationTime = sessionStore.get(sessionId);
        return expirationTime != null && expirationTime > System.currentTimeMillis();
    }

    /**
     * Invalidates the given session ID.
     *
     * @param sessionId the session ID to invalidate
     */
    public void invalidateSession(String sessionId) {
        sessionStore.remove(sessionId);
    }
}