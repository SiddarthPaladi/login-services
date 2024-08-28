package io.movecloud.movecloud_login_service.controller;

import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.openapi.AboutOperation;
import io.movecloud.movecloud_login_service.openapi.HelloOperation;
import io.movecloud.movecloud_login_service.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for Hello and About endpoints.
 *
 * <p>@author Dushyanth @since 2024.1.6
 */
@RestController
@Slf4j
public class HelloController {

    /**
     * Returns a hello message.
     *
     * @return a hello message
     */
    @GetMapping(Constants.HELLO_PATH)
    @HelloOperation
    public String hello() {
        log.info(Constants.REQUEST_RECEIVED_LOG, Constants.HELLO_PATH);
        try {
            String response = Constants.HELLO_MESSAGE;
            log.info(Constants.RESPONSE_SENT_LOG, response);
            return response;
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_HELLO_CONTROLLER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_HELLO_CONTROLLER_LOG);
        }
    }

    /**
     * Returns an about message with the session ID.
     *
     * @param request the HTTP request
     * @return an about message with the session ID
     */
    @GetMapping(Constants.ABOUT_PATH)
    @AboutOperation
    public String about(HttpServletRequest request) {
        log.info(Constants.REQUEST_RECEIVED_LOG, Constants.ABOUT_PATH);
        try {
            String response = Constants.ABOUT_MESSAGE_PREFIX + request.getSession().getId();
            log.info(Constants.RESPONSE_SENT_LOG, response);
            return response;
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_HELLO_CONTROLLER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_HELLO_CONTROLLER_LOG);
        }
    }
}