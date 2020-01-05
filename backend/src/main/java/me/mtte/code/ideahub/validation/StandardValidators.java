package me.mtte.code.ideahub.validation;

import java.util.Optional;
import java.util.regex.Pattern;

public class StandardValidators {

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9]*");

    public static Validator<String> nonNull() {
        return nonNullObject -> nonNullObject != null ? Optional.empty() : Optional.of(new ValidationError("Object cannot be null"));
    }

    public static Validator<String> notEmpty() {
        return value -> value.isEmpty() ? Optional.of(new ValidationError("Value cannot be empty")) : Optional.empty();
    }

    public static Validator<String> maxLength(final int maxLength) {
        return value -> {
            if (value.length() > maxLength) {
                return Optional.of(new ValidationError("Max length of %d exceeded: '%s' ", maxLength, value));
            }
            return Optional.empty();
        };
    }

    public static Validator<String> minLength(final int minLength) {
        return value -> {
            if (value.length() < minLength) {
                return Optional.of(new ValidationError("Min length of %d not reached: '%s' ", minLength, value));
            }
            return Optional.empty();
        };
    }

    public static Validator<String> passwordDiversity() {
        return password -> {
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
