package auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private static final String EMAIL_REGEX =
        "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isStrongPassword(String password) {
        // Check for minimum length
        if (password.length() < 8) {
            return false;
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }

        return true;
    }
}
public class AuthService {

    public boolean registerUser(String email, String password) {
        if (!InputValidator.isValidEmail(email)) {
            return false; // Invalid email
        }

        if (!InputValidator.isStrongPassword(password)) {
            return false; // Weak password
        }

        // Your registration logic here...

        return true; // Registration successful
    }
}