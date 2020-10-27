package exception;

public class IllegalPasswordException extends LoginException {
    public static final String MESSAGE = "비밀번호가 일치하지 않습니다";

    public IllegalPasswordException() {
        super(MESSAGE);
    }
}
