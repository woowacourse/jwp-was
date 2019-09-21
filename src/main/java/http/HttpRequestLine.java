package http;

import http.excption.InvalidRequestException;

public class HttpRequestLine {

    private HttpRequestMethod method;
    private HttpRequestUrl url;
    private HttpProtocol protocol;

    public HttpRequestLine(String firstLine) {
        String[] splitFirstLine = firstLine.split(" ");
        checkSplitLength(splitFirstLine);
        this.method = HttpRequestMethod.of(splitFirstLine[0]);
        this.url = HttpRequestUrl.of(splitFirstLine[1]);
        this.protocol = HttpProtocol.of(splitFirstLine[2]);
    }

    private void checkSplitLength(String[] splitFirstLine) {
        if (splitFirstLine.length != 3) {
            throw new InvalidRequestException("유효하지 않은 요청입니다.");
        }
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public HttpRequestUrl getUrl() {
        return url;
    }

    public HttpProtocol getProtocol() {
        return protocol;
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
