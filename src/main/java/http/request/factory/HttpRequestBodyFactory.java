package http.request.factory;

import http.request.HttpRequestBody;

import java.util.List;

public class HttpRequestBodyFactory {
    public static HttpRequestBody create(List<String> lines) {
        return new HttpRequestBody(lines);
    }
}
