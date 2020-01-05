package me.mtte.code.ideahub.validation;

import java.util.Optional;

/**
 * Functional interface for a Validator
 */
public interface Validator<T> {

    /**
     * Validates this validator.
     * @return Optional containing a {@link ValidationError} if the validation has failed.
     */
    Optional<ValidationError> validate(T value);

    default Validator<T> and(final Validator<T> validator) {
        return value -> {
            var result = validate(value);
            if (result.isPresent()) {
                return result;
            }
            return validator.validate(value);
        };
    }

    default Validator<T> or(final Validator<T> validator) {
        return value -> {
            var result = validate(value);
            if (result.isPresent()) {
                return validator.validate(value);
            }
            return Optional.empty();
        };
    }

}
