package io.movecloud.movecloud_login_service.config;

import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.service.JwtService;
import io.movecloud.movecloud_login_service.service.MyUserService;
import io.movecloud.movecloud_login_service.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Constants.AUTHORIZATION_HEADER);
        String token = null;
        String userName = null;

        try {
            if (authHeader != null && authHeader.startsWith(Constants.BEARER_PREFIX)) {
                token = authHeader.substring(Constants.BEARER_PREFIX.length());
                userName = jwtService.extractUsername(token);
                log.info(Constants.EXTRACTED_USERNAME_LOG, userName);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = applicationContext.getBean(MyUserService.class).loadUserByUsername(userName);

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    log.info(Constants.AUTHENTICATED_USER_LOG, userName);
                } else {
                    log.warn(Constants.INVALID_TOKEN_LOG, userName);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(Constants.ERROR_IN_JWT_FILTER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_IN_JWT_FILTER_LOG);
        }
    }
}