package io.movecloud.movecloud_login_service.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get Welcome Message",
        description = "Retrieves a welcome message",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved welcome message"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
public @interface HelloOperation {
}
