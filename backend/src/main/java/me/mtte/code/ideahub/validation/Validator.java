package me.mtte.code.ideahub.validation;

import java.util.Optional;

/**
 * Functional interface for a Validator
 */
interface Validator {

    /**
     * Validates this validator.
     * @return Optional containing a {@link ValidationError} if the validation has failed.
     */
    Optional<ValidationError> validate();

}
