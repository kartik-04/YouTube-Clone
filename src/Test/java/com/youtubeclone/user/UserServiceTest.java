package com.youtubeclone.user;

import com.youtubeclone.user.User;
import com.youtubeclone.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    @DisplayName("registerUser : should register user if there is no email")
    void testRegisterUser() {

    }

//    public User registerUser(String username, String passwordHash, String email, LocalDate DOB) {
//        for(User user : userStore.values()) {
//            if(user.getEmail().equalsIgnoreCase(email)){
//                return null;
//            }
//        }
//        UUID userId  = UUID.randomUUID();
//        User newUser = new User(userId, username, passwordHash, email, DOB.atStartOfDay());
//        newUser.setAccountStatus(User.AccountStatus.PENDING_EMAIL_VERIFICATION);
//        userStore.put(userId, newUser);
//        return newUser;
//    }
}