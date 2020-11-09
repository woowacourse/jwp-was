package http;

import java.util.Map;

public class HttpRequestLine {
    private static final String DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private final HttpMethod httpMethod;
    private final HttpUrl httpUrl;
    private final String httpVersion;

    private HttpRequestLine(HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestLine from(String requestLine) {
        String[] tokens = requestLine.split(DELIMITER);
        HttpMethod httpMethod = HttpMethod.from(tokens[METHOD_INDEX]);
        HttpUrl httpUrl = HttpUrl.from(tokens[URL_INDEX]);
        String httpVersion = tokens[VERSION_INDEX];
        return new HttpRequestLine(httpMethod, httpUrl, httpVersion);
    }

    public String getUrl() {
        return httpUrl.getUrl();
    }

    public Map<String, String> getBody() {
        return httpUrl.getParams();
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getVersion() {
        return httpVersion;
    }
}
