package kr.wootecat.dongle.model.http.exception;

import kr.wootecat.dongle.model.http.HttpMethod;

public class MethodNotAllowedException extends RuntimeException {

    private static final String NOT_ALLOWED_METHOD_EXCEPTION_MESSAGE = "%s 타입 메서드는 지원하지 않습니다.";

    public MethodNotAllowedException(HttpMethod httpMethod) {
        super(String.format(NOT_ALLOWED_METHOD_EXCEPTION_MESSAGE, httpMethod.name()));
    }
}
