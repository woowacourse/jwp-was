package http.request;

import http.common.HttpMethod;

import java.io.IOException;
import java.net.URLDecoder;

public class RequestLine {
    private static final String QUERY_FLAG = "\\?";
    private static final String BLANK = " ";
    private static final String SLASH = "/";

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

    public static RequestLine of(String startLine) throws IOException {
        String[] token = startLine.split(BLANK);
        HttpMethod method = HttpMethod.valueOf(token[0]);
        String requestUrl = URLDecoder.decode(token[1], "UTF-8");
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
        try {
            return requestUrl.split(QUERY_FLAG)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getQueryString() {
        return queryString;
    }

    public String getProtocol() {
        return httpVersion.split(SLASH)[0].toLowerCase();
    }
}
