package webserver.http.request;

import webserver.http.response.GetHttpResponse;
import webserver.http.response.HttpResponse;

import java.util.Arrays;

public enum HttpRequestMethod {
    GET("GET", new GetHttpResponse());

    private final String methodType;
    private final HttpResponse httpResponse;

    HttpRequestMethod(String methodType, HttpResponse httpResponse) {
        this.methodType = methodType;
        this.httpResponse = httpResponse;
    }

    public static HttpResponse getHttpResponse(String methodType) {
        return Arrays.stream(values())
                .filter(httpRequestMethod -> httpRequestMethod.methodType.equals(methodType))
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .httpResponse;
    }
}
