package model;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("잘못된 유저입니다.");
    }
}
