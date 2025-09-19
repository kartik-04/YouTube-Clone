package com.youtubeclone.util.user;

import com.youtubeclone.Models.user.Creator;
import com.youtubeclone.Models.user.User;
import com.youtubeclone.Models.user.Viewer;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for user-related validation and helper functions.
 */
public class UserUtil {

    /**
     * Validates whether the provided email address is in a proper format.
     *
     * @param email The email address to validate.
     * @return true if the email format is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        // Regex for most common email formats (not 100% RFC-compliant but solid for most uses)
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(regex);
    }

    /**
     * Checks if the provided password is strong.
     * A strong password should:
     * - Be at least 8 characters long
     * - Contain at least one uppercase letter
     * - Contain at least one lowercase letter
     * - Contain at least one digit
     * - Contain at least one special character (e.g., @#$%^&+=!)
     *
     * @param password The password string to validate.
     * @return true if the password is strong, false otherwise.
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.trim().isEmpty()) return false;

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }

    /**
     * Stubbed method to simulate email verification.
     * In a real-world system, this would check an email verification database or token system.
     *
     * @param email The email to verify.
     * @return true if the email is considered "verified", false otherwise.
     */
    public static boolean isVerified(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        // Simulated rule: any email ending with "@gmail.com" is treated as verified
        return email.toLowerCase().endsWith("@gmail.com");
    }

    /**
     * Checks whether the user is an adult (18 years or older).
     *
     * @param dob Date of birth of the user.
     * @return true if user is at least 18 years old, false otherwise.
     */
    public static boolean isAdult(LocalDate dob) {
        if (dob == null) return false;

        Period age = Period.between(dob, LocalDate.now());
        return age.getYears() >= 18;
    }

    // Generic method that works for any subtype of User
    public static <T extends User> void printUserInfo(T user) {
        System.out.println("User Info:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());

            // Downcast if needed
        if (user instanceof Viewer) {
            ((Viewer) user).browseVideos(); // or any viewer-specific method
        } else if (user instanceof Creator) {
            ((Creator) user).accessStudio();
        }
    }
}
