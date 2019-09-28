package http.request.support;

public class HttpRequestLine {
    public static final String DELIMITER_OF_REQUEST_LINE = ":";

    private final String key;
    private final String value;

    public HttpRequestLine(final String line) {
        int index = line.indexOf(DELIMITER_OF_REQUEST_LINE);
        this.key = line.substring(0, index);
        this.value = line.substring(index + 1);
    }

    public String getKey() {
        return key.trim();
    }

    public String getValue() {
        return value.trim();
    }
}
