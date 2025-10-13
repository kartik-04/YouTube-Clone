package com.youtube_clone.reaction.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI youtubeCloneOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("YouTube Clone Reaction Service API")
                        .description("API documentation for Reaction microservice")
                        .version("1.0"));
    }
}