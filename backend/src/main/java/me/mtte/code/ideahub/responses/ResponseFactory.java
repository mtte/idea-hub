package me.mtte.code.ideahub.responses;

import spark.Request;
import spark.Response;

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

}
