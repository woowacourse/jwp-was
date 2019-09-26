package exception;

public class InvalidSignUpDataException extends RuntimeException {
    public static final String INVALID_SIGN_UP_DATA_MESSAGE = "올바르지 않은 회원가입 요청입니다.";

    public InvalidSignUpDataException() {
        super(INVALID_SIGN_UP_DATA_MESSAGE);
    }
}
