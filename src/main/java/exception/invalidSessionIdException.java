package exception;

public class invalidSessionIdException extends RuntimeException {
    private static final String INVALID_SESSIONID_MESSAGE = "세션 아이디가 존재하지 않습니다.";

    public invalidSessionIdException() {
        super(INVALID_SESSIONID_MESSAGE);
    }
}
