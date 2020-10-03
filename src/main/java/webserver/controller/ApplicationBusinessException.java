package webserver.controller;

public class ApplicationBusinessException extends RuntimeException {
    public ApplicationBusinessException(String message) {
        super(message);
    }
}
