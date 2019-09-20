package http;

public class HttpRequestLine {

    private String method;
    private String url;
    private String protocol;

    public HttpRequestLine(String firstLine) {
        String[] splitFirstLine = firstLine.split(" ");
        checkSplitLength(splitFirstLine);
        this.method = splitFirstLine[0];
        this.url = splitFirstLine[1];
        this.protocol = splitFirstLine[2];
    }

    private void checkSplitLength(String[] splitFirstLine) {
        if (splitFirstLine.length != 3) {
            throw new InvalidRequestException("유효하지 않은 요청입니다.");
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }
}
