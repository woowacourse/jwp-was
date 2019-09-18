package http.request;

import http.common.HttpMethod;
import http.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

public class RequestStartLine {
    private static final String QUERY_FLAG = "\\?";
    private static final String BLANK = " ";
    private static final String SLASH = "/";

    private HttpMethod method;
    private String requestUrl;
    private String httpVersion;

    private RequestStartLine(HttpMethod method, String requestUrl, String httpVersion) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.httpVersion = httpVersion;
    }

    public static RequestStartLine of(BufferedReader br) throws IOException {
        String startLine = br.readLine();
        String[] token = startLine.split(BLANK);
        HttpMethod method = HttpMethod.valueOf(token[0]);
        String requestUrl = URLDecoder.decode(token[1], "UTF-8");
        String httpVersion = token[2];

        return new RequestStartLine(method, requestUrl, httpVersion);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return requestUrl.split(QUERY_FLAG)[0];
    }

    public String getQueryString() {
        try {
            return requestUrl.split(QUERY_FLAG)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getProtocol() {
        return httpVersion.split(SLASH)[0].toLowerCase();
    }

    public Map<String, String> getQuery() {
        return HttpUtils.parseQuery(getQueryString());
    }
}
