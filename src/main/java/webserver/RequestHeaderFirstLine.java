package webserver;

public class RequestHeaderFirstLine {

    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String ROOT_PATH = "/";
    private static final String INDEX_PATH = "/index";
    private static final String INDEX_SUFFIX = ".html";
    private static final String HTTP_HEADER_FIRST_LINE_DELIMITER = " ";

    private final String method;
    private final String resourcePath;
    private final String version;

    public RequestHeaderFirstLine(String line) {
        String[] lineSegment = line.split(HTTP_HEADER_FIRST_LINE_DELIMITER);
        validate(lineSegment);
        this.method = lineSegment[0];
        this.resourcePath = lineSegment[1];
        this.version = lineSegment[2];
    }

    private void validate(String[] lineSegment) {
        if (lineSegment.length != 3) {
            throw new NegativeArraySizeException("Header의 FirstLine 사이즈가 맞지 않습니다!");
        }
    }

    public boolean isGet() {
        return HTTP_METHOD_GET.equals(method);
    }

    public boolean isPost() {
        return HTTP_METHOD_POST.equals(method);
    }

    public String getMethod() {
        return method;
    }

    public String getResourcePath() {
        if (ROOT_PATH.equals(resourcePath)) {
            return INDEX_PATH + INDEX_SUFFIX;
        }
        return resourcePath;
    }

    public String getVersion() {
        return version;
    }
}
