package com.youtubeclone.services.Interfaces;

import com.youtubeclone.user.User;

import java.time.LocalDate;
import java.util.UUID;

public interface IUserService {
    User registerUser(String username, String passwordHash, String email, LocalDate DOB);
    User authenticateUser(String email, String passwordHash);
    User getUserByID(UUID id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User updateProfile(UUID id, String newUsername, String passwordHash, String newGender);
    User deleteUser(UUID id);
    User updatePassword(UUID id, String oldPassword, String newPassword);
    void updateAccountStatus(UUID id, User.AccountStatus newStatus);
    User verifyEmail(UUID id);
    User deactivateUser(UUID id);
    User suspendedUser(UUID id);
}
