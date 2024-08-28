package io.movecloud.movecloud_login_service.dto;

import lombok.Data;

/**
 * Data Transfer Object for login operations.
 *
 * <p>This class holds the necessary information for a user to log in, including the business email,
 * encrypted password, and organization name.
 *
 * <p>\@author Dushyanth \@since 2024.1.6
 */
@Data
public class LoginDto {
    private String email;
    private String password;
    private String companyName;
}