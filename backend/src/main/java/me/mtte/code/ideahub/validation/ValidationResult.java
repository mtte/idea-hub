package me.mtte.code.ideahub.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<ValidationError> errors = new ArrayList<>();

    ValidationResult validate(Validator v) {
        v.validate().ifPresent(this::addError);
        return this;
    }

    private void addError(ValidationError error) {
        errors.add(error);
    }

    public boolean failed() {
        return !errors.isEmpty();
    }

    public boolean succeeded() {
        return errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return List.copyOf(errors);
    }

}
