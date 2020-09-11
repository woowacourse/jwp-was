package controller;

import http.HttpMethod;
import http.RequestUri;

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

    public static Optional<ControllerMapper> from(RequestUri requestUri) {
        return Arrays.stream(ControllerMapper.values())
                .filter(requestUri::isEqualRequestType)
                .findAny();
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }
}
