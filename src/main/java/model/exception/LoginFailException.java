package model.exception;

public class LoginFailException extends RuntimeException{
    public LoginFailException(String message) {
        super(message);
    }
}
