package com.youtubeclone.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a user in the YouTube clone platform.
 * This class includes core user information, roles, account status, and timestamps.
 */
public class User {

    /** Unique identifier for the user */
    private final UUID id;

    /** Username chosen by the user */
    private String username;

    /** Encrypted password of the user (store securely in production) */
    private String passwordHash;

    /** Email address of the user */
    private String email;

    /** Date of birth of the user */
    private LocalDate DOB;

    /** Timestamp when the account was created */
    private final LocalDateTime createdAt;

    /** Timestamp of the last update to the user profile */
    private LocalDateTime updatedAt;

    /** Gender of the user */
    private String gender;

    /** Current account status of the user */
    private AccountStatus accountStatus;

    /** Set of roles assigned to the user (e.g., VIEWER, CREATOR) */
    private Set<UserRoles> roles;

    /**
     * Enumeration for user account statuses.
     */
    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        PENDING_EMAIL_VERIFICATION,
        DELETED
    }

    /**
     * Enumeration for possible roles a user can have.
     */
    public enum UserRoles {
        VIEWER,
        CREATOR,
        ADMIN
    }

    /**
     * Default constructor initializes fields with default or null values.
     * Useful for serialization/deserialization and test mocks.
     */
    public User(){
        this.id = null;
        this.username = null;
        this.passwordHash = null;
        this.email = null;
        this.DOB = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.gender = null;
        this.roles = new HashSet<>();
        this.accountStatus = AccountStatus.PENDING_EMAIL_VERIFICATION;
    }

    /**
     * Parameterized constructor to create a new user with required fields.
     *
     * @param id        Unique user identifier
     * @param username  User's chosen username
     * @param password  User's password (should be hashed)
     * @param email     User's email address
     * @param DOB       User's date of birth
     */
    public User(UUID id, String username,
                String password, String email, LocalDate DOB) {
        this.id = id;
        this.username = username;
        this.passwordHash = password;
        this.email = email;
        this.DOB = DOB;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.accountStatus = AccountStatus.PENDING_EMAIL_VERIFICATION;
    }

    /** @return User ID */
    public UUID getId() {
        return this.id;
    }

    /** @return Username */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return User email */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email.
     * @param email New email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return User's date of birth */
    public LocalDate getDOB() {
        return DOB;
    }

    /**
     * Sets the user's date of birth.
     * @param DOB Date of birth
     */
    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    /** @return Account creation timestamp */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @return Last profile update timestamp */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Updates the profile's last modified timestamp.
     * @param updatedAt Timestamp of last update
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** @return Current account status */
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the user's account status.
     * @param accountStatus New account status
     */
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /** @return Set of user roles */
    public Set<UserRoles> getRoles() {
        return roles;
    }

    /**
     * Sets the roles for the user.
     * @param roles Set of UserRoles
     */
    public void setRoles(Set<UserRoles> roles) {
        if(this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles = roles;
    }

    /** @return UserHndle call */
    public void handle(){
        System.out.println(getUsername()+" is browsing user.");
    }

    /** @return User's gender */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     * @param gender User's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns a string representation of the user object.
     * Does not include password for security reasons.
     *
     * @return Formatted user data
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", DOB=" + DOB +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", gender='" + gender + '\'' +
                ", accStatus=" + accountStatus +
                ", roles=" + roles +
                '}';
    }
}