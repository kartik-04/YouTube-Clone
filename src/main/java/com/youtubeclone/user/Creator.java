package com.youtubeclone.user;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Creator extends User {
    // creating object for this class
    public Creator(UUID id, String username, String password, String email, LocalDateTime DOB) {
        super(id, username, password, email, DOB);
        setRoles(Set.of(UserRoles.CREATOR));
    }

    public void accessStudio(){
        System.out.println(getUsername()+" is browsing studio.");
    }

    // using polymorphism over here
    public void handle(){
        this.accessStudio();
    }
}
