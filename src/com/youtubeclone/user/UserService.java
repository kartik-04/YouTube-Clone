package com.youtubeclone.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class UserService {
    private final Map<UUID, User> userStore = new HashMap<>();

    public User registerUser(String username, String passwordHash, String email, LocalDate DOB) {
        for(User user : userStore.values()) {
            if(user.getEmail().equalsIgnoreCase(email)){
                return null;
            }
        }
        UUID userId  = UUID.randomUUID();
        User newUser = new User(userId, username, passwordHash, email, DOB.atStartOfDay());
        newUser.setAccountStatus(User.AccountStatus.PENDING_EMAIL_VERIFICATION);
        userStore.put(userId, newUser);
        return newUser;
    }
}
