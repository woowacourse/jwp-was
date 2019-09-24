package http.supoort.converter.request;

import http.exceptions.IllegalHttpRequestException;
import http.model.request.ServletRequest;

import java.io.BufferedReader;
import java.util.Arrays;

public enum HttpRequestConverter {
    GET(new GetHttpRequestMessageconverter()),
    DELETE(new DeleteHttpRequestMethodConverter()),
    POST(new PostHttpRequestMessageconverter()),
    PUT(new PutHttpRequestMessageconverter());

    private HttpRequestMessageconverter converter;

    private HttpRequestConverter(HttpRequestMessageconverter converter) {
        this.converter = converter;
    }

    public static HttpRequestConverter of(String httpMethod) {
        return Arrays.stream(HttpRequestConverter.values())
                .filter(converter -> converter.name().equals(httpMethod))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }

    public ServletRequest convert(String uri, String protocol, BufferedReader bufferedReader) {
        return converter.convert(uri, protocol, bufferedReader);
    }
}
