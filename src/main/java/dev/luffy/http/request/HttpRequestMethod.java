package dev.luffy.http.request;

import java.util.Arrays;

import dev.luffy.http.excption.NotSupportedHttpRequestMethodException;

public enum HttpRequestMethod {
    GET, POST;

    public static final String NOT_SUPPORTED_HTTP_REQUEST_MESSAGE = "지원하지 않는 요청 메서드 입니다.";

    public static HttpRequestMethod of(String method) {
        return Arrays.stream(values())
                .filter(requestMethod -> requestMethod.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new NotSupportedHttpRequestMethodException(NOT_SUPPORTED_HTTP_REQUEST_MESSAGE));
    }
}
