package me.mtte.code.ideahub.validation;

import java.util.Optional;

public class StandardValidators {

    public static Validator<String> nonNull() {
        return nonNullObject -> nonNullObject != null ? Optional.empty() : Optional.of(new ValidationError("Object cannot be null"));
    }

    public static Validator<String> notEmpty() {
        return value -> value.isEmpty() ? Optional.of(new ValidationError("Value cannot be empty")) : Optional.empty();
    }

    public static Validator<String> maxLength(final int maxLength) {
        return value -> {
            if (value.length() > maxLength) {
                return Optional.of(new ValidationError("Max length of %d exceeded", maxLength));
            }
            return Optional.empty();
        };
    }

    public static Validator<String> minLength(final int minLength) {
        return value -> {
            if (value.length() < minLength) {
                return Optional.of(new ValidationError("Min length of %d not reached", minLength));
            }
            return Optional.empty();
        };
    }

}
