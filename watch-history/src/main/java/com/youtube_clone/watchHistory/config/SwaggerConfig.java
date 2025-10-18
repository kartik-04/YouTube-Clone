package com.youtube_clone.watchHistory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI watchHistoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Watch History Service API")
                        .description("""
                                This service manages users' video watch activities — including session tracking,
                                view duration validation, and owner-specific view limits.
                                
                                **Core Responsibilities:**
                                - Record valid views (>30s)
                                - Enforce 5-views-per-day-per-user limit
                                - Track IP and session times
                                - Distinguish owner vs non-owner views
                                """)
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Kartik Singh")
                                .email("04kartik04@gmail.com")
                                .url("https://github.com/kartiklsingh"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}