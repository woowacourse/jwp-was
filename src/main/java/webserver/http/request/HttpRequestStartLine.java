package webserver.http.request;

import webserver.http.HttpVersion;

public class HttpRequestStartLine {
    private static final String START_LINE_REGEX = " ";
    private static final int HTTP_METHOD_TYPE_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;

    private final HttpMethodType httpMethodType;
    private final String url;
    private final HttpVersion httpVersion;

    private HttpRequestStartLine(HttpMethodType httpMethodType, String url, HttpVersion httpVersion) {
        this.httpMethodType = httpMethodType;
        this.url = url;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestStartLine of(String startLine) {
        String[] startLines = startLine.split(START_LINE_REGEX);
        HttpMethodType httpMethodType = HttpMethodType.find(startLines[HTTP_METHOD_TYPE_INDEX]);
        HttpVersion httpVersion = HttpVersion.find(startLines[HTTP_VERSION_INDEX]);
        return new HttpRequestStartLine(httpMethodType, startLines[URL_INDEX], httpVersion);
    }

    public HttpMethodType getHttpMethodType() {
        return httpMethodType;
    }

    public String getUrl() {
        return url;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
