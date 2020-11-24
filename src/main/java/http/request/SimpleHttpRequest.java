package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

import http.HttpBody;
import http.HttpHeaders;
import http.HttpMethod;
import http.HttpSession;
import http.HttpVersion;
import utils.IOUtils;

public class SimpleHttpRequest implements HttpRequest {
    private StartLine startLine;
    private HttpHeaders headers;
    private HttpBody httpBody;
    private HttpSession httpSession;
    private String rawRequest;

    public static SimpleHttpRequest of(BufferedReader bufferedReader) throws IOException {
        String startLineString = bufferedReader.readLine();
        StartLine startLine = StartLine.from(startLineString);
        String requestHeadersString = extractRequestHeader(bufferedReader);
        HttpHeaders httpHeaders = HttpHeaders.from(requestHeadersString);
        String requestBodyString = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
        HttpBody httpBody = HttpBody.from(requestBodyString);
        return new SimpleHttpRequest(startLine, httpHeaders, httpBody,
                joinRequest(startLineString, requestHeadersString, requestBodyString));
    }

    private static String extractRequestHeader(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (Objects.nonNull(line = bufferedReader.readLine()) && !line.isEmpty()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private static String joinRequest(String startLine, String httpHeaders, String requestBody) {
        return startLine
                + System.lineSeparator()
                + httpHeaders
                + System.lineSeparator()
                + System.lineSeparator()
                + requestBody;
    }

    private SimpleHttpRequest(StartLine startLine, HttpHeaders httpHeaders, HttpBody httpBody,
            String rawRequest) {
        this.startLine = startLine;
        this.headers = httpHeaders;
        this.httpBody = httpBody;
        this.httpSession = headers.getSession();
        this.rawRequest = rawRequest;
    }

    @Override
    public HttpMethod getMethod() {
        return startLine.getHttpMethod();
    }

    @Override
    public String getURI() {
        return startLine.getPath();
    }

    @Override
    public String getQuery() {
        return startLine.getQuery();
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public HttpBody getBody() {
        return httpBody;
    }

    @Override
    public HttpSession getSession() {
        return httpSession;
    }

    @Override
    public HttpVersion getVersion() {
        return HttpVersion.from(startLine.getVersion());
    }

    @Override
    public String toString() {
        return rawRequest;
    }
}
