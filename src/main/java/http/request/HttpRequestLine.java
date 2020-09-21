package http.request;

import http.HttpMethod;
import http.HttpUri;

public class HttpRequestLine {
    private static final String DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final int REQUIRED_LENGTH = 3;

    private final HttpMethod method;
    private final HttpUri path;
    private final String version;

    private HttpRequestLine(HttpMethod method, HttpUri path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static HttpRequestLine from(String line) {
        String[] tokens = line.split(DELIMITER);
        if (tokens.length != REQUIRED_LENGTH) {
            throw new IllegalArgumentException("METHOD URI VERSION 형식이어야 합니다.");
        }
        return new HttpRequestLine(HttpMethod.from(tokens[METHOD_INDEX]), HttpUri.from(tokens[PATH_INDEX]),
            tokens[VERSION_INDEX]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getParam(String key) {
        return path.getParam(key);
    }

    public String getVersion() {
        return version;
    }
}
