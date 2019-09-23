package webserver.request;

public class RequestLine {
    private static final String SPACE = " ";
    private static final int WORD_COUNT = 3;
    private static final String SEPARATOR = "\\?";
    private static final String QUESTION_MARK = "?";

    private String method;
    private String uri;
    private String version;

    public RequestLine(String requestLine) {
        String[] splitFirstLine = requestLine.split(SPACE);
        checkRequestFirstLine(splitFirstLine);
        this.method = splitFirstLine[0];
        this.uri = splitFirstLine[1];
        this.version = splitFirstLine[2];
    }

    private void checkRequestFirstLine(String[] splitFirstLine) {
        if (splitFirstLine.length != WORD_COUNT) {
            throw new IllegalArgumentException("잘못된 Http 요청입니다.");
        }
    }

    public String getUri() {
        return uri.split(SEPARATOR)[0];
    }

    public boolean hasParams() {
        return uri.contains(QUESTION_MARK);
    }

    public String getQueryString() {
        return hasParams() ? uri.split(SEPARATOR)[1] : null;
    }

    public String getMethod() {
        return method;
    }

    public String getFullUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }
}
