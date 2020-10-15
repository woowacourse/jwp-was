package application.controller;

public class WrongUserIdPasswordException extends Exception {

    public WrongUserIdPasswordException(String message) {
        super(message);
    }
}
