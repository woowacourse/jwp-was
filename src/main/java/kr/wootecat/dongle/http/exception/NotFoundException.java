package kr.wootecat.dongle.http.exception;

public class NotFoundException extends RuntimeException {

    private static final String NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "다음 경로의 요청에 해당하는 리소스가 존재하지 않습니다. : %s";

    public NotFoundException(String requestPath) {
        super(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, requestPath));
    }
}
