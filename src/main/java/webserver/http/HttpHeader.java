package webserver.http;

public class HttpHeader {
    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_TYPE_INDEX = 0;
    private static final int HEADER_CONTENT_INDEX = 1;

    private final String type;
    private final String content;

    private HttpHeader(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public static HttpHeader of(String line) {
        String[] lines = line.split(HEADER_DELIMITER);
        return new HttpHeader(lines[HEADER_TYPE_INDEX], lines[HEADER_CONTENT_INDEX]);
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
