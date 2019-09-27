package http.request;

public class HttpRequestLine {
    private final String key;
    private final String value;

    public HttpRequestLine(final String line) {
        String[] header = line.split(":");
        this.key = header[0];
        this.value = header[1];
    }

    public String getKey() {
        return key.trim();
    }

    public String getValue() {
        return value.trim();
    }
}
