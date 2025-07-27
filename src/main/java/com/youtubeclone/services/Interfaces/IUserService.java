package com.youtubeclone.services.Interfaces;

import com.youtubeclone.user.User;

import java.time.LocalDate;
import java.util.UUID;

public interface IUserService {
    public User registerUser(String username, String passwordHash, String email, LocalDate DOB);
    public User authenticateUser(String email, String passwordHash);
    public User getUserByID(UUID id);
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
    public User updateProfile(UUID id, String newUsername, String passwordHash, String newGender);
    public User deleteUser(UUID id);
    public User updatePassword(UUID id, String oldPassword, String newPassword);
    public void updateAccountStatus(UUID id, User.AccountStatus newStatus);
    public User verifyEmail(UUID id);
    public User deactivateUser(UUID id);
    public User suspendedUser(UUID id);
}
