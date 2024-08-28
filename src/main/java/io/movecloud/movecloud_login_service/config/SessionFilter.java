package io.movecloud.movecloud_login_service.config;

import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.service.SessionService;
import io.movecloud.movecloud_login_service.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class SessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String sessionId = request.getHeader(Constants.SESSION_ID_HEADER);
        log.info(Constants.SESSION_ID_LOG, sessionId);

        try {
            if (sessionId == null || !sessionService.isSessionValid(sessionId)) {
                log.warn(Constants.INVALID_SESSION_LOG);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(Constants.INVALID_SESSION_MESSAGE);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_SESSION_FILTER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_SESSION_FILTER_LOG);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals(Constants.LOGIN_PATH) || path.equals(Constants.REGISTER_PATH);
    }
}