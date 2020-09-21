package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import http.HttpMethod;

public class HttpRequest {
    private final HttpRequestLine requestLine;

    private HttpRequest(HttpRequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        HttpRequestLine requestLine = HttpRequestLine.from(reader.readLine());
        return new HttpRequest(requestLine);
    }

    public HttpMethod method() {
        return requestLine.getMethod();
    }

    public String path() {
        return requestLine.getPath();
    }

    public String version() {
        return requestLine.getVersion();
    }
}
