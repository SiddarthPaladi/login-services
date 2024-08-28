package io.movecloud.movecloud_login_service.config;

import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private SessionFilter sessionFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.info(Constants.CONFIGURING_AUTH_PROVIDER_LOG);
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(Constants.BCRYPT_STRENGTH));
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws MoveCloudException {
        try {
            log.info(Constants.CONFIGURING_SECURITY_FILTER_CHAIN_LOG);
            httpSecurity
                    .csrf(customizer -> customizer.disable())
                    .authorizeHttpRequests(request -> request
                            .requestMatchers(Constants.LOGIN_PATH, Constants.REGISTER_PATH).permitAll()
                            .anyRequest().authenticated())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        } catch (Exception e) {
            log.error(Constants.ERROR_CONFIGURING_SECURITY_FILTER_CHAIN_LOG, e);
            throw new MoveCloudException(Constants.ERROR_CONFIGURING_SECURITY_FILTER_CHAIN_LOG);
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws MoveCloudException {
        try {
            log.info(Constants.CONFIGURING_AUTH_MANAGER_LOG);
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            log.error(Constants.ERROR_CONFIGURING_AUTH_MANAGER_LOG, e);
            throw new MoveCloudException(Constants.ERROR_CONFIGURING_AUTH_MANAGER_LOG);
        }
    }
}