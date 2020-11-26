package http.request;

import http.ContentType;
import http.HttpHeaders;
import http.HttpMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import utils.HttpUtils;
import utils.IOUtils;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";

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
        HttpHeaders headers = HttpHeaders.from(IOUtils.readLineUntilEmpty(reader));
        String body = "";
        if (headers.has(CONTENT_LENGTH)) {
            body = IOUtils.readData(reader, Integer.parseInt(headers.get(CONTENT_LENGTH)));
        }
        if (ContentType.APPLICATION_X_WWW_FORM_URLENCODED.match(headers.get(CONTENT_TYPE))) {
            body = HttpUtils.urlDecode(body);
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

    public List<String> getParams(String key) {
        return requestLine.getParams(key);
    }

    public String version() {
        return requestLine.getVersion();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getBody() {
        return body;
    }
}
