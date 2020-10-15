package web;

public class RequestLine {

    private static final String SPACE = " ";

    private HttpMethod method;
    private String path;
    private String version;

    private RequestLine(HttpMethod method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static RequestLine from(String line) {
        String[] values = line.split(SPACE);
        return new RequestLine(HttpMethod.from(values[0]), values[1], values[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }
}
