package model.exception;

public class InvalidPasswordException extends RuntimeException {
    private static final String INVALID_PASSWORD_EXCEPTION_MESSAGE = "잘못된 비밀번호입니다.";

    public InvalidPasswordException() {
        super(INVALID_PASSWORD_EXCEPTION_MESSAGE);
    }
}
