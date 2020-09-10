package http;

import java.util.Map;

public class HttpStartLine {
    private static final String START_LINE_DELIMITER = " ";

    private String method;
    private HttpUrl httpUrl;
    private String version;

    private HttpStartLine(String method, String url, String version) {
        this.method = method;
        this.httpUrl = new HttpUrl(url);
        this.version = version;
    }

    public HttpStartLine(String startLine) {
        String[] splitedStartLine = startLine.split(START_LINE_DELIMITER);
        this.method = splitedStartLine[0];
        this.httpUrl = new HttpUrl(splitedStartLine[1]);
        this.version = splitedStartLine[2];
    }

    public boolean isSamePath(String path) {
        return httpUrl.isSamePath(path);
    }

    public String getUrl() {
        return httpUrl.getUrl();
    }

    public String getPath() {
        return httpUrl.getPath();
    }

    public Map<String, String> getParameters() {
        return httpUrl.getParameters();
    }
}
