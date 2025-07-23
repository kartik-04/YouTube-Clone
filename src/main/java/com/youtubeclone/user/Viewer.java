package com.youtubeclone.user;

import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Viewer extends User{

    // Creating viewer function to create viewer
    public Viewer(UUID id, String username, String passwordHash, String email, LocalDate DOB) {
        super(id, username, passwordHash, email, DOB);
        setRoles(Set.of(UserRoles.VIEWER));
    }

    // Creating browseVideo method
    public void browseVideos(){
        System.out.println(getUsername()+" is browsing video.");
    }

    // overriding the method present in parent class
    @Override
    public void handle(){
        this.browseVideos();
    }
}
