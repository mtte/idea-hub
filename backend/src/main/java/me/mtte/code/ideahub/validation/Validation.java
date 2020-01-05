package me.mtte.code.ideahub.validation;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Offers utility methods for input validation.
 */
public class Validation {

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9]*");

    private final ValidationResult result = new ValidationResult();

    public boolean failed() {
        return this.result.failed();
    }

    public boolean succeeded() {
        return this.result.succeeded();
    }

    public ValidationResult getResult() {
        return this.result;
    }

    public Validation validateUsername(String username) {
        result.validate(nonNull(username))
                .validate(maxLength(50, username));
        return this;
    }

    public Validation validatePassword(String password) {
        result.validate(nonNull(password))
                .validate(minLength(12, password))
                .validate(maxLength(50, password))
                .validate(passwordDiversity(password));
        return this;
    }

    public Validation validateRole(String role) {
        result.validate(nonNull(role))
                .validate(notEmpty(role));
        return this;
    }

    private static Validator nonNull(final Object nonNullObject) {
        return () -> nonNullObject != null ? Optional.empty() : Optional.of(new ValidationError("Object cannot be null"));
    }

    private static Validator notEmpty(final String value) {
        return () -> value.isEmpty() ? Optional.of(new ValidationError("Value cannot be empty")) : Optional.empty();
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
                return Optional.of(new ValidationError("Password has to meet at least 3 of the following categories: " +
                        "lower case, upper case, numbers, special characters"));
            }

            return Optional.empty();
        };
    }

}
