package com.youtube_clone.reaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactionApplication.class, args);
    }
}
