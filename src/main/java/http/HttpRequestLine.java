package http;

import http.excption.InvalidRequestException;

public class HttpRequestLine {

    private static final int FIRST_LINE_SPEC = 3;
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String INVALID_REQUEST_MESSAGE = "유효하지 않은 요청입니다.";

    private HttpRequestMethod method;
    private HttpRequestUrl url;
    private HttpProtocol protocol;

    public HttpRequestLine(String firstLine) {
        String[] splitFirstLine = firstLine.split(REQUEST_LINE_DELIMITER);
        checkSplitLength(splitFirstLine);
        this.method = HttpRequestMethod.of(splitFirstLine[0]);
        this.url = HttpRequestUrl.of(splitFirstLine[1]);
        this.protocol = HttpProtocol.of(splitFirstLine[2]);
    }

    private void checkSplitLength(String[] splitFirstLine) {
        if (splitFirstLine.length != FIRST_LINE_SPEC) {
            throw new InvalidRequestException(INVALID_REQUEST_MESSAGE);
        }
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public HttpRequestUrl getUrl() {
        return url;
    }

    public String getPath() {
        return url.getPath();
    }

    public HttpProtocol getProtocol() {
        return protocol;
    }

    public boolean hasExtension() {
        return url.hasExtension();
    }

    public String getExtension() {
        return url.getExtension();
    }

    @Override
    public String toString() {
        return "HttpRequestLine{" +
                "method=" + method +
                ", url=" + url +
                ", protocol=" + protocol +
                '}';
    }
}
