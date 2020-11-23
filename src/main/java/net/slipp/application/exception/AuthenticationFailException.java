package net.slipp.application.exception;

public class AuthenticationFailException extends RuntimeException {

    private static final String AUTHENTICATION_FAIL_EXCEPTION_MESSAGE = "인증에 실패했습니다.";

    public AuthenticationFailException() {
        super(AUTHENTICATION_FAIL_EXCEPTION_MESSAGE);
    }
}
