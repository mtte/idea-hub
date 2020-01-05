package me.mtte.code.ideahub.validation;

import java.util.Optional;

/**
 * Validates a value with a validator.
 */
public class Validation<T> {

    private ValidationError error;

    public Validation(T value, Validator<T> validator) {
        Optional<ValidationError> result = validator.validate(value);
        result.ifPresent(validationError -> this.error = validationError);
    }

    public boolean failed() {
        return this.error != null;
    }

    public ValidationError getError() {
        return this.error;
    }

}
