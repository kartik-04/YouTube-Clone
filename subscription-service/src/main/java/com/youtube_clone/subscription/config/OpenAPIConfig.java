package com.youtube_clone.subscription.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

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