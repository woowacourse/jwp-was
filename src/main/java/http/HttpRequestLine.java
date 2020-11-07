package http;

public class HttpRequestLine {
    public static final String DELIMITER = " ";
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
        HttpMethod httpMethod = HttpMethod.from(tokens[0]);
        HttpUrl httpUrl = HttpUrl.from(tokens[1]);
        String httpVersion = tokens[2];
        return new HttpRequestLine(httpMethod, httpUrl, httpVersion);
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public HttpUrl getUrl() {
        return httpUrl;
    }

    public String getVersion() {
        return httpVersion;
    }
}
