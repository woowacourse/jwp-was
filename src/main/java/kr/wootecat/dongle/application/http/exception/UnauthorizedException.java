package kr.wootecat.dongle.application.http.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "인증에 실패했습니다.";

    public UnauthorizedException() {
        super(UNAUTHORIZED_EXCEPTION_MESSAGE);
    }
}
