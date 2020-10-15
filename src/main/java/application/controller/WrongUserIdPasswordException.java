package application.controller;

public class WrongUserIdPasswordException extends RuntimeException {

    public WrongUserIdPasswordException(String message) {
        super(message);
    }
}
