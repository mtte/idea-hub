package me.mtte.code.ideahub.validation;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Offers utility methods for input validation.
 */
public class Validation {

    public static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    public static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    public static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    public static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9]*");

    public Validation() {
        throw new IllegalStateException("Utility class");
    }

    public static ValidationResult validateUsername(String username) {
        return new ValidationResult()
                .validate(maxLength(50, username));
    }

    public static ValidationResult validatePassword(String password) {
        return new ValidationResult()
                .validate(minLength(12, password))
                .validate(maxLength(50, password))
                .validate(passwordDiversity(password));
    }

    private static Validator maxLength(final int maxLength, final String value) {
        return () -> {
            if (value.length() > maxLength) {
                return Optional.of(new ValidationError("Max length of %d exceeded: '%s' ", maxLength, value));
            }
            return Optional.empty();
        };
    }

    private static Validator minLength(final int minLength, final String value) {
        return () -> {
            if (value.length() < minLength) {
                return Optional.of(new ValidationError("Min length of %d not reached: '%s' ", minLength, value));
            }
            return Optional.empty();
        };
    }

    private static Validator passwordDiversity(final String password) {
        return () -> {
            int categoriesMet = 0;

            if (LOWER_CASE_PATTERN.matcher(password).find()) {
                categoriesMet++;
            }
            if (UPPER_CASE_PATTERN.matcher(password).find()) {
                categoriesMet++;
            }
            if (DIGIT_PATTERN.matcher(password).find()) {
                categoriesMet++;
            }
            if (SPECIAL_CHARS_PATTERN.matcher(password).find()) {
                categoriesMet++;
            }

            if (categoriesMet < 3) {
                return Optional.of(new ValidationError("Password has to contain at least 3 categories: " +
                        "lower case, upper case, numbers, special characters"));
            }

            return Optional.empty();
        };
    }

}
