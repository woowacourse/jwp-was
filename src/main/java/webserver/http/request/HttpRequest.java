package webserver.http.request;

import webserver.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final String COOKIE_DELIMITER_REGEX = "; ";
    private static final String COOKIE_JSESSIONID = "jsessionid";

    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpHeaders httpHeaders;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String startLine = bufferedReader.readLine();
        httpRequestStartLine = HttpRequestStartLine.of(startLine);
        httpHeaders = HttpHeaders.of(bufferedReader);
        HttpHeader contentLengthHeader = HttpHeader.of(HttpHeaderType.CONTENT_LENGTH);
        httpRequestBody = initHttpRequestBody(bufferedReader, contentLengthHeader);
    }

    private HttpRequestBody initHttpRequestBody(BufferedReader bufferedReader, HttpHeader contentLengthHeader)
            throws IOException {
        if (httpHeaders.contains(contentLengthHeader)) {
            String contentLength = httpHeaders.getHttpHeader(contentLengthHeader);
            return HttpRequestBody.of(bufferedReader, contentLength);
        }
        return HttpRequestBody.emptyRequestBody();
    }

    public boolean isGetRequest() {
        return httpRequestStartLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return httpRequestStartLine.isPostRequest();
    }

    public List<String> getJSessionCookies() {
        String[] cookieAttributes = parseCookie();
        return Arrays.stream(cookieAttributes)
                .filter(attribute -> attribute.contains(COOKIE_JSESSIONID))
                .collect(Collectors.toList());
    }

    private String[] parseCookie() {
        String cookieHeader = getHeader(HttpHeader.of(HttpHeaderType.COOKIE));
        return cookieHeader.split(COOKIE_DELIMITER_REGEX);
    }

    public URL getUrl() {
        return httpRequestStartLine.getUrl();
    }

    public String getMethod() {
        return httpRequestStartLine.getHttpMethodType().name();
    }

    public String getHeader(HttpHeader httpHeader) {
        return httpHeaders.getHttpHeader(httpHeader);
    }

    public String getQueryParameter(String key) {
        return httpRequestStartLine.getParameter(key);
    }

    public String getBodyParameter(String key) {
        return httpRequestBody.getParameters().getParameter(key);
    }

    public Parameters getBodyParameters() {
        return httpRequestBody.getParameters();
    }
}
