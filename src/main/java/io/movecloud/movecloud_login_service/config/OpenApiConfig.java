package io.movecloud.movecloud_login_service.config;

import io.movecloud.movecloud_login_service.utils.Constants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This class configures the OpenAPI documentation for the MoveCloud Login Service API. It sets
 * up the API's base URL, title, version, and description.
 *
 * @author Dushyanth
 * @since 2024.1.6
 */
@Configuration
public class OpenApiConfig {

  /** Base URL for the API, injected via application properties. */
  @Value(Constants.$_API_BASE_URL)
  private String baseUrl;

  /**
   * Configures the OpenAPI documentation with the specified server URL, title, version, and
   * description.
   *
   * @return an OpenAPI instance with the configured settings
   */
  @Bean
  public OpenAPI customOpenAPI() {
    // Create a new server instance and set its URL
    Server server = new Server();
    server.setUrl(baseUrl);

    // Create and return an OpenAPI instance with the specified information
    return new OpenAPI()
        .info(
            new Info()
                .title("MoveCloud Login Service API")
                .version("1.0.0")
                .description("API for MoveCloud Login Service"))
        .servers(List.of(server));
  }
}
