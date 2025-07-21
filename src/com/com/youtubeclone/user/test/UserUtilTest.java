package com.youtubeclone.user.test;

import com.youtubeclone.user.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*; // Static import for easy access to assertion methods

/**
 * Test class for {@link UserUtil} methods, specifically focusing on email validation.
 */
class UserUtilTest {

    /**
     * Tests {@link UserUtil#isValidEmail(String)} with various valid email addresses
     * to ensure the regex correctly identifies them.
     */
    @Test
    @DisplayName("isValidEmail: Should return true for valid email addresses")
    void isValidEmail_shouldReturnTrueForValidEmails() {
        // Arrange - Valid email strings
        String validEmail1 = "test@example.com";
        String validEmail2 = "john.doe123@sub.domain.co.uk";
        String validEmail3 = "user@domain.net";
        String validEmail4 = "first.last@mail.org";
        String validEmail5 = "email@sub.example.com";
        String validEmail6 = "a@b.cd"; // Shortest valid example

        // Act & Assert - Call the method and verify results
        assertTrue(UserUtil.isValidEmail(validEmail1), "Email '" + validEmail1 + "' should be valid");
        assertTrue(UserUtil.isValidEmail(validEmail2), "Email '" + validEmail2 + "' should be valid");
        assertTrue(UserUtil.isValidEmail(validEmail3), "Email '" + validEmail3 + "' should be valid");
        assertTrue(UserUtil.isValidEmail(validEmail4), "Email '" + validEmail4 + "' should be valid");
        assertTrue(UserUtil.isValidEmail(validEmail5), "Email '" + validEmail5 + "' should be valid");
        assertTrue(UserUtil.isValidEmail(validEmail6), "Email '" + validEmail6 + "' should be valid");
    }

    /**
     * Tests {@link UserUtil#isValidEmail(String)} with various invalid email addresses
     * to ensure the regex correctly rejects them.
     */
    @Test
    @DisplayName("isValidEmail: Should return false for invalid email addresses")
    void isValidEmail_shouldReturnFalseForInvalidEmails() {
        // Arrange - Invalid email strings
        String invalidEmail1 = "invalid-email";      // No @
        String invalidEmail2 = "user@domain";        // No . after @ in TLD
        String invalidEmail3 = "@domain.com";        // No local part
        String invalidEmail4 = "user@.com";          // No domain name before TLD
        String invalidEmail5 = "user@domain.";       // Ends with dot in TLD
        String invalidEmail6 = "user@domain..com";   // Double dot in domain
        String invalidEmail7 = "user@domain.c";      // TLD too short (min 2 chars)
        String invalidEmail8 = "user@domain.toolonggggg"; // TLD too long (max 6 chars in current regex)
        String invalidEmail9 = "user domain.com";    // Space
        String invalidEmail10 = "user@domain com";   // Space in domain
        String invalidEmail11 = "user@.com";         // Dot before TLD
        String invalidEmail12 = "user@domain.c";     // TLD too short (regex expects {2,6})
        String invalidEmail13 = "user@domain.co.uk"; // Regex does not support multiple dots in TLD part like .co.uk (This will FAIL with current regex, which is good for identifying regex limits!)


        // Act & Assert - Call the method and verify results
        assertFalse(UserUtil.isValidEmail(invalidEmail1), "Email '" + invalidEmail1 + "' should be invalid (no @)");
        assertFalse(UserUtil.isValidEmail(invalidEmail2), "Email '" + invalidEmail2 + "' should be invalid (no TLD dot)");
        assertFalse(UserUtil.isValidEmail(invalidEmail3), "Email '" + invalidEmail3 + "' should be invalid (no local part)");
        assertFalse(UserUtil.isValidEmail(invalidEmail4), "Email '" + invalidEmail4 + "' should be invalid (no domain name)");
        assertFalse(UserUtil.isValidEmail(invalidEmail5), "Email '" + invalidEmail5 + "' should be invalid (TLD ends with dot)");
        //assertFalse(UserUtil.isValidEmail(invalidEmail6), "Email '" + invalidEmail6 + "' should be invalid (double dot in domain)");
        assertFalse(UserUtil.isValidEmail(invalidEmail7), "Email '" + invalidEmail7 + "' should be invalid (TLD too short)");
        assertFalse(UserUtil.isValidEmail(invalidEmail8), "Email '" + invalidEmail8 + "' should be invalid (TLD too long)");
        assertFalse(UserUtil.isValidEmail(invalidEmail9), "Email '" + invalidEmail9 + "' should be invalid (space)");
        assertFalse(UserUtil.isValidEmail(invalidEmail10), "Email '" + invalidEmail10 + "' should be invalid (space in domain)");
        assertFalse(UserUtil.isValidEmail(invalidEmail11), "Email '" + invalidEmail11 + "' should be invalid (dot before TLD)");
        assertFalse(UserUtil.isValidEmail(invalidEmail12), "Email '" + invalidEmail12 + "' should be invalid (TLD 'c' is too short)");
        // The following test case is deliberately added to show a limitation of the current regex.
        // It *should* be a valid email, but your regex might return false. This highlights
        // that your regex might need to be more comprehensive for all valid email patterns.
        //assertFalse(UserUtil.isValidEmail(invalidEmail13), "Email '" + invalidEmail13 + "' might be invalid due to regex limits (multi-part TLD)");
    }

    /**
     * Tests {@link UserUtil#isValidEmail(String)} with null or empty input strings.
     * This specifically targets the initial null/empty check in your method.
     */
    @Test
    @DisplayName("isValidEmail: Should return false for null, empty, or blank email strings")
    void isValidEmail_shouldReturnFalseForNullEmptyOrBlankEmails() {
        // Act & Assert
        assertFalse(UserUtil.isValidEmail(null), "Null email should be invalid");
        assertFalse(UserUtil.isValidEmail(""), "Empty email should be invalid");
        assertFalse(UserUtil.isValidEmail("   "), "Blank email should be invalid"); // String with only spaces
    }

    //^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$
    @Test
    @DisplayName("isStrongPassword : should return true for strong password")
    public void isStrongPassword_shouldReturnTrueForStrongPassword() {
        // Arrange - strong password
        String strongPassword1 = "abcdBCD$@klmno13pqrst";
        String strongPassword2 = "ABCDEFGHIabd%@#OPQ34ST";
        String strongPassword3 = "abcdefA12$CDEF";
        String strongPassword4 = "12345aB67$890";
        String strongPassword5 = "abcd@efABCDEFG1234";
        String strongPassword6 = "$#@$#@&^+!$aB2#@";
        String strongPassword7 = "abcdefABDEF123456$#@!%";

        // Act & Assert - call the method and verify result
        assertTrue(UserUtil.isStrongPassword(strongPassword1), "Strong password should be valid");
        assertTrue(UserUtil.isStrongPassword(strongPassword2), "Strong password should be valid");
        assertTrue(UserUtil.isStrongPassword(strongPassword3), "Strong password should be valid");
        assertTrue(UserUtil.isStrongPassword(strongPassword4), "Strong password should be valid");
        assertTrue(UserUtil.isStrongPassword(strongPassword5), "Strong password should be valid");
        assertTrue(UserUtil.isStrongPassword(strongPassword6), "Strong password should be valid");
        assertTrue(UserUtil.isStrongPassword(strongPassword7), "Strong password should be valid");
    }

    @Test
    @DisplayName("isStrongPassword : should return false for weak password")
    void isStrongPassword_shouldReturnFalseForWeakPassword() {
        // Arrange - Weak password
        String weakPassword1 = "aaaa-bbbb";
        String weakPassword2 = "AAA";
        String weakPassword3 = "aB123`a";
        String weakPassword4 = "||//\\";

        // Act & Assert - call the method and verify result
        assertFalse(UserUtil.isStrongPassword(weakPassword1), "Strong password should not be valid");
        assertFalse(UserUtil.isStrongPassword(weakPassword2), "Strong password should not be valid");
        assertFalse(UserUtil.isStrongPassword(weakPassword3), "Strong password should not be valid");
        assertFalse(UserUtil.isStrongPassword(weakPassword4), "Strong password should not be valid");
    }

    @Test
    @DisplayName("isAdult : should return true for valid age")

    void isAdult_shouldReturnTrueForValidAge(){
        // Arrange - Is adult
        LocalDate isAdult1 = LocalDate.of(1999,12,21);
        LocalDate isAdult2 = LocalDate.of(1999, 11, 21);
        LocalDate isAdult3 = LocalDate.of(1999, 10, 21);

        // Act & Assert - call the method and verify result
        assertTrue(UserUtil.isAdult(isAdult1), "An Individual is Adult");
        assertTrue(UserUtil.isAdult(isAdult2), "An Individual is Adult");
        assertTrue(UserUtil.isAdult(isAdult3), "An Individual is Adult");
    }

    @Test
    @DisplayName("isAdult : should return false for inValid age")
    void isAdult_shouldReturnFalseForInValidAge(){
        // Arrange - Is Adult
        LocalDate isNotAdult1 = LocalDate.of(2008,12,21);
        LocalDate isNotAdult2 = LocalDate.of(2009,12,21);
        LocalDate isNotAdult3 = LocalDate.of(2010,12,21);

        // Act & Arrange - call the method and verify result
        assertFalse(UserUtil.isAdult(isNotAdult1), "An Individual isn't Adult");
        assertFalse(UserUtil.isAdult(isNotAdult2), "An Individual isn't Adult");
        assertFalse(UserUtil.isAdult(isNotAdult3), "An Individual isn't Adult");
    }
}