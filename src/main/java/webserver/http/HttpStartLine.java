package webserver.http;

import java.util.Arrays;
import java.util.List;

public class HttpStartLine {
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_URL_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;

    private HttpMethod httpMethod;
    private HttpUrl httpUrl;
    private String httpVersion;

    public HttpStartLine(HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
    }

    public static HttpStartLine from(String startLine) {
        List<String> startLineElements = Arrays.asList(startLine.split(" "));

        HttpMethod httpMethod = HttpMethod.of(startLineElements.get(HTTP_METHOD_INDEX));
        HttpUrl httpUrl = HttpUrl.from(startLineElements.get(HTTP_URL_INDEX));
        String httpVersion = startLineElements.get(HTTP_VERSION_INDEX);
        return new HttpStartLine(httpMethod, httpUrl, httpVersion);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return httpUrl.getUrl();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public boolean allowBody() {
        return httpMethod.allowBody();
    }
}
