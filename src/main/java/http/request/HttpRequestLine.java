package http.request;

public class HttpRequestLine {
    private static final String START_LINE_DELIMITER = " ";

    private final HttpMethod httpMethod;
    private final HttpRequestUrl httpRequestUrl;
    private final String version;

    public HttpRequestLine(String requestLine) {
        String[] splitedStartLine = requestLine.split(START_LINE_DELIMITER);
        this.httpMethod = HttpMethod.valueOf(splitedStartLine[0]);
        this.httpRequestUrl = new HttpRequestUrl(splitedStartLine[1]);
        this.version = splitedStartLine[2];
    }

    public boolean isStaticFile() {
        return httpRequestUrl.isStaticFile();
    }

    public String getPath() {
        return httpRequestUrl.getPath();
    }

    public boolean isPost() {
        return httpMethod.isPost();
    }

    public String getContentType() {
        return httpRequestUrl.getContentType();
    }
}
