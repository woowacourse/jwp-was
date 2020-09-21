package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import http.HttpHeaders;
import http.HttpMethod;
import utils.IOUtils;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    private final String body;

    private HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        HttpRequestLine requestLine = HttpRequestLine.from(reader.readLine());
        HttpHeaders headers = HttpHeaders.from(IOUtils.readHeaders(reader));
        String body = "";
        if (headers.has("Content-Length")) {
            body = IOUtils.readData(reader, Integer.parseInt(headers.get("Content-Length")));
        }
        return new HttpRequest(requestLine, headers, body);
    }

    public HttpMethod method() {
        return requestLine.getMethod();
    }

    public String path() {
        return requestLine.getPath();
    }

    public String getParam(String key) {
        return requestLine.getParam(key);
    }

    public String version() {
        return requestLine.getVersion();
    }

    public String getBody() {
        return body;
    }
}
