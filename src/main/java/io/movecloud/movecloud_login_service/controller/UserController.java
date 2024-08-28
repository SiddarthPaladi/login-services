package io.movecloud.movecloud_login_service.controller;

import io.movecloud.movecloud_login_service.dto.LoginDto;
import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.openapi.LoginOperation;
import io.movecloud.movecloud_login_service.openapi.RegisterUserOperation;
import io.movecloud.movecloud_login_service.service.SessionService;
import io.movecloud.movecloud_login_service.service.UserService;
import io.movecloud.movecloud_login_service.model.User;
import io.movecloud.movecloud_login_service.service.JwtService;
import io.movecloud.movecloud_login_service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for user-related operations such as registration and login.
 *
 * <p>@author Dushyanth @since 2024.1.6
 */
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SessionService sessionService;

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return the registered user
     */
    @PostMapping(Constants.REGISTER_PATH)
    @RegisterUserOperation

    public User register(@RequestBody User user) {
        try {
            return userService.register(user);
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_USER_CONTROLLER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_USER_CONTROLLER_LOG);
        }
    }

    /**
     * Authenticates a user and returns a token and session ID.
     *
     * @param loginDto the login data transfer object
     * @return a map containing the token and session ID
     */
    @PostMapping(Constants.LOGIN_PATH)
    @LoginOperation
    public Map<String, String> login(@RequestBody LoginDto loginDto) {
        log.info(Constants.LOGIN_ATTEMPT_LOG, loginDto.getEmail());
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            if (authentication.isAuthenticated()) {
                log.info(Constants.LOGIN_SUCCESS_LOG, loginDto.getEmail());
                String sessionId = sessionService.createSession(loginDto.getEmail());
                String token = jwtService.generateToken(loginDto.getEmail(), loginDto.getCompanyName(), sessionId);
                Map<String, String> response = new HashMap<>();
                response.put(Constants.TOKEN_KEY, token);
                response.put(Constants.SESSION_ID_KEY, sessionId);
                return response;
            } else {
                log.warn(Constants.LOGIN_FAILED_LOG, loginDto.getEmail());
                return Collections.singletonMap(Constants.MESSAGE_KEY, Constants.LOGIN_FAILED_MESSAGE);
            }
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_USER_CONTROLLER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_USER_CONTROLLER_LOG);
        }
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of users
     */
    public List<User> getUsers() {
        try {
            return userService.getUsers();
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_USER_CONTROLLER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_USER_CONTROLLER_LOG);
        }
    }
}