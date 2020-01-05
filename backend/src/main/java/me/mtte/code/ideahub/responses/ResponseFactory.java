package me.mtte.code.ideahub.responses;

import me.mtte.code.ideahub.validation.ValidationError;
import me.mtte.code.ideahub.validation.ValidationResult;
import spark.Request;
import spark.Response;

import java.util.StringJoiner;

public class ResponseFactory {

    public ResponseFactory() {
        throw new IllegalStateException();
    }

    public static ErrorResponse createInvalidIdError(Request request, Response response) {
        response.status(400);
        return new ErrorResponse("The supplied id '%s' is invalid", request.params("id"));
    }

    public static ErrorResponse createInvalidParameterError(Response response, String parameter, String value) {
        response.status(400);
        return new ErrorResponse("Invalid Parameter: '%s' with value '%s'.", parameter, value);
    }

    public static ErrorResponse createInvalidParameterError(Response response, String parameter, String value, String message, Object... args) {
        response.status(400);
        return new ErrorResponse("Invalid Parameter: '%s' with value '%s'. %s.", parameter, value, String.format(message, args));
    }

    public static ErrorResponse createValidationErrorResponse(Response response, ValidationResult result) {
        if (result.succeeded()) {
            throw new IllegalStateException("The validation caused no error!");
        }

        response.status(400);

        StringJoiner message = new StringJoiner(", ", "Request validation failed: ", "");
        for (ValidationError error : result.getErrors()) {
            message.add(error.getError());
        }
        return new ErrorResponse(message.toString());
    }

}
