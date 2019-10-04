package http.request;

import http.common.HttpMethod;

import java.io.IOException;
import java.net.URLDecoder;

public class RequestLine {
    private static final String QUERY_FLAG = "\\?";
    private static final String BLANK = " ";
    private static final String SLASH = "/";
    private static final String UTF_8 = "UTF-8";

    private HttpMethod method;
    private String requestUrl;
    private String queryString;
    private String httpVersion;

    private RequestLine(HttpMethod method, String requestUrl, String queryString, String httpVersion) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.queryString = queryString;
        this.httpVersion = httpVersion;
    }

    public static RequestLine of(String requestLine) throws IOException {
        String[] token = requestLine.split(BLANK);
        HttpMethod method = HttpMethod.valueOf(token[0]);
        String requestUrl = URLDecoder.decode(token[1], UTF_8);
        String queryString = setQueryString(requestUrl);
        String httpVersion = token[2];

        return new RequestLine(method, requestUrl, queryString, httpVersion);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return requestUrl.split(QUERY_FLAG)[0];
    }

    private static String setQueryString(String requestUrl) {
        String[] queryString = requestUrl.split(QUERY_FLAG);
        if (queryString.length == 1) {
            return "";
        }
        return queryString[1];
    }

    public String getQueryString() {
        return queryString;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getProtocol() {
        return httpVersion.split(SLASH)[0].toLowerCase();
    }
}
