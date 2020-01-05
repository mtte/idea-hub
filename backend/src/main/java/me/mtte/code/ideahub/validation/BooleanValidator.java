package me.mtte.code.ideahub.validation;

import java.util.Optional;
import java.util.function.Predicate;

public class BooleanValidator<T> implements Validator<T> {

    private final Predicate<T> predicate;
    private String errorMessage;

    public BooleanValidator(Predicate<T> predicate, String errorMessage) {
        this.predicate = predicate;
        this.errorMessage = errorMessage;
    }

    @Override
    public Optional<ValidationError> validate(T value) {
        return this.predicate.test(value)
                ? Optional.empty()
                : Optional.of(new ValidationError(this.errorMessage));
    }

}
