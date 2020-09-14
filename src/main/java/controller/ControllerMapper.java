package controller;

import http.request.HttpMethod;
import http.request.RequestLine;

import java.util.Arrays;
import java.util.Optional;

public enum ControllerMapper {
    CREATE_USER(HttpMethod.POST, "/user/create");

    private HttpMethod httpMethod;
    private String url;

    ControllerMapper(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
    }

    public static Optional<ControllerMapper> from(RequestLine requestLine) {
        return Arrays.stream(ControllerMapper.values())
                .filter(requestLine::isEqualRequestType)
                .findAny();
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }
}
