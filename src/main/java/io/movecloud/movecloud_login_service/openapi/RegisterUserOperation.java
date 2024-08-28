package io.movecloud.movecloud_login_service.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Register a new user",
        description = "Registers a new user with the given details",
        responses = {
                @ApiResponse(responseCode = "200", description = "User registered successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid input"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
public @interface RegisterUserOperation {
}
