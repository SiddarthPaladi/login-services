package io.movecloud.movecloud_login_service.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get About and Session Information",
        description = "Retrieves information about the service and the current session",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved about and session information"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
public @interface AboutOperation {
}
