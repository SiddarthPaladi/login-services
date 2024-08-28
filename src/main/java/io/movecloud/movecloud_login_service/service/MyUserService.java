package io.movecloud.movecloud_login_service.service;

import io.movecloud.movecloud_login_service.model.User;
import io.movecloud.movecloud_login_service.model.UserPrincipal;
import io.movecloud.movecloud_login_service.repository.UserRepo;
import io.movecloud.movecloud_login_service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for loading user details by username.
 *
 * <p>This service implements the UserDetailsService interface to provide user details
 * for authentication and authorization purposes.
 *
 * <p>\@author Dushyanth \@since 2024.1.6
 */
@Service
@Slf4j
public class MyUserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    /**
     * Loads user details by username.
     *
     * @param username the username to load
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(Constants.LOADING_USER_LOG, username);
        User user = userRepo.findByBusinessEmail(username);
        if (user == null) {
            log.error(Constants.USER_NOT_FOUND_LOG, username);
            throw new UsernameNotFoundException(Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        log.info(Constants.USER_FOUND_LOG, user);
        return new UserPrincipal(user);
    }
}