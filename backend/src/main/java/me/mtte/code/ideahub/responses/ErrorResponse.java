package me.mtte.code.ideahub.responses;

public class ErrorResponse implements Response {

    private final String message;

    public ErrorResponse(String message, Object... args) {
        this.message = String.format(message, args);
    }

    public ErrorResponse(Exception e) {
        this.message = e.getMessage();
    }

}
