package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import exceptions.InvalidHttpRequestException;
import http.HttpMethod;

public class HttpRequestParser {

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);

        HttpMethod method = requestLine.getMethod();
        if (HttpMethod.GET == method) {
            return new HttpRequest(requestLine, requestHeader, null);
        }
        if (HttpMethod.POST == method) {
            RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());
            return new HttpRequest(requestLine, requestHeader, requestBody);
        }
        throw new InvalidHttpRequestException();
    }

}
