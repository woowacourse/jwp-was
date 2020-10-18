package exception;

public class NotFoundUserIdException extends LoginException {
    public static final String MESSAGE = "존재하지 않는 아이디입니다";

    public NotFoundUserIdException() {
        super(MESSAGE);
    }
}
