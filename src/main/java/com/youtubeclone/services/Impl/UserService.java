package com.youtubeclone.services.Impl;

import com.youtubeclone.services.Interfaces.IUserService;
import com.youtubeclone.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.LocalDate;

import static com.youtubeclone.util.UserUtil.*;

/**
 * Service class responsible for user-related operations such as
 * registration, authentication, profile management, and status updates.
 */
public class UserService implements IUserService {

    /** In-memory storage for user data (simulates a database) */
    private final Map<UUID, User> userStore = new HashMap<>();

    /**
     * Registers a new user if the email doesn't already exist.
     *
     * @param username     Chosen username
     * @param passwordHash Hashed password
     * @param email        User's email address
     * @param DOB          Date of birth
     * @return Newly created User object or null if email already exists
     */
    public User registerUser(String username, String passwordHash, String email, LocalDate DOB) {
        for(User user : userStore.values()) {
            if(user.getEmail().equalsIgnoreCase(email)){
                return null;
            }
        }
        UUID userId  = UUID.randomUUID();
        User newUser = new User(userId, username, passwordHash, email, DOB);
        newUser.setAccountStatus(User.AccountStatus.PENDING_EMAIL_VERIFICATION);
        userStore.put(userId, newUser);
        return newUser;
    }

    /**
     * Authenticates a user using email and password hash.
     *
     * @param email         Email used for login
     * @param passwordHash  Hashed password
     * @return Authenticated User or null if credentials are invalid
     */
    public User authenticateUser(String email, String passwordHash){
        for(User user : userStore.values()){
            if(user.getEmail().equalsIgnoreCase(email) && user.getPasswordHash().equals(passwordHash)){
                return user;
            }
        }
        return null;
    }

    /**
     * Fetches a user by their unique UUID.
     *
     * @param id User ID
     * @return User object if found, otherwise null
     */
    public User getUserByID(UUID id) {
        return userStore.get(id);
    }

    /**
     * Finds a user by their username.
     *
     * @param username User's username
     * @return User object or null if not found
     */
    public User getUserByUsername(String username) {
        for(User user : userStore.values()){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by email.
     *
     * @param email User's email address
     * @return User object or null if not found
     */
    public User getUserByEmail(String email) {
        for (User user : userStore.values()) {
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    /**
     * Updates the user profile's username and gender if the password matches.
     *
     * @param id            User ID
     * @param newUsername   New username
     * @param passwordHash  Current password hash (for verification)
     * @param newGender     New gender value
     * @return Updated User object or null if validation fails
     */
    public User updateProfile(UUID id, String newUsername, String passwordHash, String newGender){
        User user = userStore.get(id);
        if(user != null && user.getPasswordHash().equals(passwordHash)){
            user.setUsername(newUsername);
            user.setGender(newGender);
            user.setUpdatedAt(LocalDateTime.now());
            return user;
        }
        return null;
    }

    /**
     * Deletes the user associated with the given ID.
     *
     * @param id User ID
     * @return
     */
    public User deleteUser(UUID id) {
        return userStore.remove(id);
    }

    /**
     * Changes the user's password if the old one matches.
     *
     * @param newPassword   New password hash
     * @param oldPassword   Old password hash
     * @param id            User ID
     * @return Updated User object or null if validation fails
     */
    public User updatePassword(UUID id, String oldPassword, String newPassword){
        User user = userStore.get(id);
        if(user != null && user.getPasswordHash().equals(oldPassword)){
            user.setPasswordHash(newPassword);
            user.setUpdatedAt(LocalDateTime.now());
            return user;
        }
        return null;
    }

    /**
     * Updates the account status of a user (e.g., ACTIVE, SUSPENDED).
     *
     * @param id        User ID
     * @param newStatus New account status
     */
    public void updateAccountStatus(UUID id, User.AccountStatus newStatus){
        User user = userStore.get(id);
        if(user != null ){
            user.setAccountStatus(newStatus);
            user.setUpdatedAt(LocalDateTime.now());
        }
    }

    /**
     * Marks email as verified and activates the account if verification is successful.
     *
     * @param id User ID
     */
    public User verifyEmail(UUID id){
        User user = userStore.get(id);
        if(user != null){
            String email = user.getEmail();
            boolean emailVerified = isVerified(email);
            if(emailVerified){
                user.setAccountStatus(User.AccountStatus.ACTIVE);
                user.setUpdatedAt(LocalDateTime.now());
                return user;
            }
        }
        return null;
    }

    public User deactivateUser(UUID id){
        User user = userStore.get(id);
        if(user != null){
            user.setAccountStatus(User.AccountStatus.DELETED);
            user.setUpdatedAt(LocalDateTime.now());
            return user;
        }
        return null;
    }

    public User suspendedUser(UUID id){
        User user = userStore.get(id);
        if(user != null){
            user.setAccountStatus(User.AccountStatus.SUSPENDED);
            user.setUpdatedAt(LocalDateTime.now());
            return  user;
        }
        return null;
    }

    /** creating userHandle function **/
    public void handleUser(User user){
        user.handle();
    }
}