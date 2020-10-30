package web.http;

public class HttpRequestLine {

    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "\\?";
    private static final int REQUEST_LINE_SIZE = 3;

    private final HttpMethod method;
    private final String path;
    private final String version;
    private final String rawPath;

    private HttpRequestLine(HttpMethod method, String path, String version, String rawPath) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.rawPath = rawPath;
    }

    public static HttpRequestLine from(String line) {
        String[] values = line.split(SPACE);
        if (values.length != REQUEST_LINE_SIZE) {
            throw new IllegalArgumentException();
        }
        return new HttpRequestLine(HttpMethod.from(values[0]), values[1].split(QUESTION_MARK)[0], values[2], values[1]);
    }

    public boolean isPost() {
        return HttpMethod.POST == method;
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

    public String getRawPath() {
        return rawPath;
    }
}
