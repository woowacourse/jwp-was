package http.supoort.converter.request;

import http.exceptions.IllegalHttpRequestException;
import http.model.request.ServletRequest;

import java.io.BufferedReader;
import java.util.Arrays;

public enum HttpRequestConverters {
    GET(new GetRequestConverter()),
    DELETE(new DeleteRequestConverter()),
    POST(new PostRequestConverter()),
    PUT(new PutRequestConverter());

    private HttpRequestConverter converter;

    private HttpRequestConverters(HttpRequestConverter converter) {
        this.converter = converter;
    }

    public static HttpRequestConverters of(String httpMethod) {
        return Arrays.stream(HttpRequestConverters.values())
                .filter(converter -> converter.name().equals(httpMethod))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }

    public ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return converter.convert(uri, protocol, bufferedReader);
    }
}
