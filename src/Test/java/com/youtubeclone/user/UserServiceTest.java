package com.youtubeclone.user;

import com.youtubeclone.Models.user.User;
import com.youtubeclone.services.Impl.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123" +
                "";

        User result = userService.registerUser(username,passwordHash,email,DOB);
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
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        // Act
        User result = userService.authenticateUser(email, passwordHash);
        // assert
        assertNotNull(result, "User should not be null");
        assertEquals(email, result.getEmail(), "Email should match");
        assertEquals(passwordHash, result.getPasswordHash(), "Password hash should match");
    }

    @Test
    @DisplayName("should return user when given Id as input")
    public void getUserById() {
        // Arrange - set Attribute
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";

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
    public void shouldReturnUserByUsername() {
        // Arrange
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";

        // Act
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        String userName = registerUser.getUsername();

        // Assert
        User result = userService.getUserByUsername(userName);
        assertNotNull(userName, "Username should not be null");
        assertEquals(username, result.getUsername(), "Username should match");
    }

    @Test
    @DisplayName("should return user when given email ")
    public void shouldReturnUserByEmail() {
        // Arrange
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";

        // Act
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        String userEmail = registerUser.getEmail();

        // Assert
        User result = userService.getUserByEmail(userEmail);
        assertNotNull(result, "User should not be null");
        assertEquals(userEmail, result.getEmail(), "Username should match");
    }

    @Test
    @DisplayName("should delete user with help of Id")
    public void shouldDeleteUserByIdt() {
        // Arrange - set Attribute
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";

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
    public void updateProfileAndSetGender() {
        //Arrange
        String username = "kartik04";
        String passwordHash = "Example_123";
        String email = "example@gmail.com";
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

    @Test
    @DisplayName("should update the password")
    public void updatePassword() {
        //Arrange
        String username = "kartik04";
        String passwordHash = "Example_123";
        String email = "example@gmail.com";
        LocalDate DOB = LocalDate.now();
        String passwordHashNew = "Kartik04_";
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Act
        User result = userService.updatePassword(userId,passwordHash,passwordHashNew);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(passwordHashNew, result.getPasswordHash(), "Password hash should match");
    }

    @Test
    @DisplayName("should udpate User with User ID")
    public void updateAccountStatusWithUserId() {
        // Arrange - set Attribute
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";
        User.AccountStatus status = User.AccountStatus.ACTIVE;
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Arrange
        userService.updateAccountStatus(userId, status);
        assertEquals(status, User.AccountStatus.ACTIVE, "Status should be ACTIVE");
    }

    @Test
    @DisplayName("should return User if email is verified")
    public void shouldReturnEmailIf_EmailIsVerified() {
        // Arrange - set Attribute
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Arrange
        User result = userService.verifyEmail(userId);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(email, result.getEmail(), "Email should match");
    }

    @Test
    @DisplayName("should deactive user with given Id")
    public void shouldReturnUserNDeactivateUser() {
        // Arrange - set Attribute
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Arrange
        User result = userService.deactivateUser(userId);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(User.AccountStatus.DELETED, result.getAccountStatus(), "Account status should match");
    }

    @Test
    @DisplayName("should suspend user with given ID")
    public void shouldReturnUserNSuspendUserTest() {
        // Arrange - set Attribute
        String email = "example@gmail.com";
        String username = "kartik04";
        LocalDate DOB = LocalDate.now();
        String passwordHash = "Example_123";
        User registerUser = userService.registerUser(username,passwordHash,email,DOB);
        UUID userId = registerUser.getId();
        // Act
        User result = userService.suspendedUser(userId);
        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(User.AccountStatus.SUSPENDED, result.getAccountStatus(),  "Account status should match");
    }
}