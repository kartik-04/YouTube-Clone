package com.youtube_clone.watchHistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = "com.youtube_clone.watchHistory.entities")
@EnableJpaRepositories(basePackages = "com.youtube_clone.watchHistory.repositories")
public class WatchHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchHistoryApplication.class, args);
    }

}
