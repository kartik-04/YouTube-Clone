package com.youtubeclone.user;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Admin extends User {

    // Creating the object of admin
    public Admin(UUID id, String userName, String password, String email, LocalDateTime DOB){
        super(id, userName, password, email, DOB);
        setRoles(Set.of(UserRoles.ADMIN));
    }

    // creating native function
    public void dashBoard(){
        System.out.println(getUsername() + " is visible.");
    }

    //
    @Override
    public void handle(){
        this.dashBoard();
    }
}
