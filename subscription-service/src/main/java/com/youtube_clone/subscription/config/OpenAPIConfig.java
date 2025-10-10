package com.youtube_clone.subscription.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for the Subscription service.
 * Defines API metadata (title, version, description, contact) for Swagger UI.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Creates and configures the {@link OpenAPI} bean used by springdoc-openapi to
     * generate the OpenAPI specification and Swagger UI.
     *
     * @return an {@link OpenAPI} instance with service title, version, description, and contact info
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("YouTube Clone Subscription Service API")
                        .version("1.0")
                        .description("API documentation for Subscription microservice of YouTube Clone project")
                        .contact(new Contact()
                                .name("Kartik Singh")
                                .email("kartik@example.com")
                                .url("https://github.com/kartik")));
    }
}