package http;

public class HttpRequestLine {
    private static final String START_LINE_DELIMITER = " ";

    private final HttpMethod httpMethod;
    private final HttpUrl httpUrl;
    private final String version;

    public HttpRequestLine(String requestLine) {
        String[] splitedStartLine = requestLine.split(START_LINE_DELIMITER);
        this.httpMethod = HttpMethod.valueOf(splitedStartLine[0]);
        this.httpUrl = new HttpUrl(splitedStartLine[1]);
        this.version = splitedStartLine[2];
    }

    public boolean isStaticFile() {
        return httpUrl.isStaticFile();
    }

    public String getPath() {
        return httpUrl.getPath();
    }

    public boolean isPost() {
        return httpMethod.isPost();
    }

    public String getContentType() {
        return httpUrl.getContentType();
    }
}
