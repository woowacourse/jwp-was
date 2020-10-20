package web;

public class RequestLine {

    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "\\?";

    private HttpMethod method;
    private String path;
    private String version;
    private String rawPath;

    private RequestLine(HttpMethod method, String path, String version, String rawPath) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.rawPath = rawPath;
    }

    public static RequestLine from(String line) {
        String[] values = line.split(SPACE);
        if (values.length != 3) {
            throw new IllegalArgumentException();
        }
        return new RequestLine(HttpMethod.from(values[0]), values[1].split(QUESTION_MARK)[0], values[2], values[1]);
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
