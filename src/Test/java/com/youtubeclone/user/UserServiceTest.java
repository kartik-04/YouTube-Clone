package com.youtubeclone.user;

import com.youtubeclone.user.User;
import com.youtubeclone.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setup() {
        userService = new UserService();
    }

    @AfterEach
    public void teardown() {
        userService = null;
    }

    @Test
    @DisplayName("should register user if email is not duplicate")
    public void registerUserIfEmailIsNotDuplicate() {
        // Arrange
        String email = "04kartik04@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String paswordHash = "Kartik_04";

        User result = userService.registerUser(username,paswordHash,email,DOB);
        // Act & assert
        assertNotNull(result, "User should not be null");
        assertEquals(username, result.getUsername(), "Username should match");
        assertEquals(email, result.getEmail(), "Email should match");
        assertEquals(DOB, result.getDOB(), "DOB should match");
        assertEquals(User.AccountStatus.PENDING_EMAIL_VERIFICATION, result.getAccountStatus(), "Account status should match");
    }
}