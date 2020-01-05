package me.mtte.code.ideahub.validation;

/**
 * An error when a validation was not successful.
 */
public class ValidationError {

    private final String error;

    /**
     * Constructor.
     * @param error The error message.
     * @param args Formatting arguments.
     */
    ValidationError(String error, Object... args) {
        this.error = String.format(error, args);
    }

    public String getMessage() {
        return error;
    }

}
