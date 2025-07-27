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

    @Test
    @DisplayName("authenticate User if email and password match")
    public void authenticateUserIfEmailAndPassword(){
        // Arrange - set attribute
        String email = "04kartik04@gmail.com";
        String passwordHash = "Kartik_04";

        User result = userService.authenticateUser(email, passwordHash);

        // Act & assert
        assertNotNull(result, "User should not be null");
        assertEquals(email, result.getEmail(), "Email should match");
        assertEquals(passwordHash, result.getPasswordHash(), "Password hash should match");
    }

    @Test
    @DisplayName("should return user when given Id as input")
    public void getUserById() {
        // Arrange - set Attribute
        String email = "04kartik04@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Kartik_04";

        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Act
        User result = userService.getUserByID(userId);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(userId, result.getId(), "User id should match");
    }

    @Test
    @DisplayName("should return user by username")
    public void getUserByUsername() {
        // Arrange
        String email = "04kartik04@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Kartik_04";

        // Act
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        String userName = registerUser.getUsername();

        // Assert
        User result = userService.getUserByUsername(userName);
        assertNotNull(userName, "Username should not be null");
        assertEquals(username, userName, "Username should match");
    }

    @Test
    @DisplayName("should return user when given email ")
    public void getUserByEmail() {
        // Arrange
        String email = "04kartik04@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Kartik_04";

        // Act
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        String userEmail = registerUser.getEmail();

        // Assert
        User result = userService.getUserByEmail(userEmail);
        assertNotNull(userEmail, "Username should not be null");
        assertEquals(username, userEmail, "Username should match");
    }

    @Test
    @DisplayName("should delete user with help of Id")
    public void deleteUserById() {
        // Arrange - set Attribute
        String email = "04kartik04@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Kartik_04";

        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Act
        User result = userService.deleteUser(userId);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(userId, result.getId(), "User id should match");
    }

    @Test
    @DisplayName("should update profile & set gender")
    public void updateProfile() {
        //Arrange
        String username = "kartik04";
        String passwordHash = "Kartik_04";
        String email = "04kartik04@gmail.com";
        LocalDate DOB = LocalDate.now();
        String  gender = "Male";
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Act
        User result = userService.updateProfile(userId,username,passwordHash,gender);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(gender, result.getGender(), "Gender should match");
    }


}