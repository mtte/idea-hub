package me.mtte.code.ideahub.validation;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Validates if a password is divers enough.
 *
 * This succeeds if the password meets 3 out of the following categories:
 * <ul>
 *    <li>contains lower case letter</li>
 *    <li>contains upper case letter</li>
 *    <li>contains lower special letter</li>
 *    <li>contains digit</li>
 * </ul>
 */
public class PasswordDiversityValidator implements Validator<String> {

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9]*");

    @Override
    public Optional<ValidationError> validate(String password) {
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
    }

}
