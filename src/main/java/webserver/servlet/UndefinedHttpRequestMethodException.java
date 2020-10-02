package webserver.servlet;

import webserver.domain.request.HttpRequest;

public class UndefinedHttpRequestMethodException extends RuntimeException {
    private static final String message ="HTTP 요청의 URL에 대한 메서드는 정의되어 있지 않습니다.";

    private UndefinedHttpRequestMethodException(String message) {
        super(message);
    }

    UndefinedHttpRequestMethodException(HttpRequest httpRequest) {
        this(String.format("%s : {'URL' : %s, 'Method': %s}", message, httpRequest.getPath(), httpRequest.getMethod()));
    }
}
