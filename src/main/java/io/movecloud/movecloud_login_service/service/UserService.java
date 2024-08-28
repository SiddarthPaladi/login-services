package io.movecloud.movecloud_login_service.service;

import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.model.User;
import io.movecloud.movecloud_login_service.repository.UserRepo;
import io.movecloud.movecloud_login_service.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling user-related operations such as registration and retrieval.
 *
 * <p>\@author Dushyanth \@since 2024.1.6
 */
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(Constants.BCRYPT_STRENGTH);

    /**
     * Registers a new user by encrypting their password and saving them to the repository.
     *
     * @param user the user to register
     * @return the registered user
     * @throws MoveCloudException if an error occurs during registration
     */
    public User register(User user) {
        try {
            user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
            return userRepo.save(user);
        } catch (Exception e) {
            throw new MoveCloudException(Constants.ERROR_REGISTERING_USER);
        }
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of users
     */
    public List<User> getUsers() {
        return userRepo.findAll();
    }
}