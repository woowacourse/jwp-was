package webserver.http.request;

import java.util.regex.Pattern;

import webserver.http.HttpMethod;

class HttpRequestLine {

    private static final Pattern EXTENSION_PATTERN = Pattern.compile("[^\\s]+\\.[\\w]+");

    private final HttpMethod method;
    private final String path;
    private final String version;

    public HttpRequestLine(HttpMethod method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public boolean isFileRequest() {
        return EXTENSION_PATTERN.matcher(path).matches();
    }

    public String getPath() {
        return this.path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean isGetMethod() {
        return method.isGet();
    }
}
