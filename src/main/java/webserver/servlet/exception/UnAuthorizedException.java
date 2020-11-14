package webserver.servlet.exception;

public class UnAuthorizedException extends RuntimeException {
    private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "인증에 실패했습니다.";

    public UnAuthorizedException() {
        super(UNAUTHORIZED_EXCEPTION_MESSAGE);
    }
}
