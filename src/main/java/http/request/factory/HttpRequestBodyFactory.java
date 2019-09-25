package http.request.factory;

import http.request.HttpRequestBody;

public class HttpRequestBodyFactory {
    public static HttpRequestBody create(String line) {
        return new HttpRequestBody(line);
    }
}
