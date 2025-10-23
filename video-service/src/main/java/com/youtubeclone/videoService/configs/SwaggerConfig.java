package com.youtubeclone.videoService.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("!test") // Exclude from tests to prevent issues
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI videoServiceOpenAPI() {
        log.info("Initializing OpenAPI configuration");
        return new OpenAPI()
                .servers(List.of(
                    new Server()
                        .url("http://localhost:" + serverPort)
                        .description("Local Development Server")
                ))
                .info(new Info()
                        .title("Video Service API")
                        .description("Manages all video upload, retrieval, and metadata operations.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Kartik Singh")
                                .email("04kartik04@gmail.com")
                                .url("https://github.com/kartiklsingh"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        log.info("Configuring GroupedOpenApi for video-service");
        return GroupedOpenApi.builder()
                .group("video-service")
                .pathsToMatch(
                    "/api/**"
                )
                .build();
    }
}