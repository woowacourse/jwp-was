package kr.wootecat.dongle.model.http;

import static java.lang.String.*;

import java.util.Arrays;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

public enum HttpMethod {

    GET, POST;

    private static final String UNSUPPORTED_METHOD_MESSAGE_FORMAT = "지원하는 메서드 타입이 아닙니다.: %s";

    public static HttpMethod of(String methodType) {
        return Arrays.stream(values())
                .filter(protocolVersion -> protocolVersion.name().equalsIgnoreCase(methodType))
                .findAny()
                .orElseThrow(() -> new IllegalRequestDataFormatException(
                        format(UNSUPPORTED_METHOD_MESSAGE_FORMAT, methodType)));
    }

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }
}
