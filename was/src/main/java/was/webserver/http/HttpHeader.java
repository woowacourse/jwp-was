package was.webserver.http;

import java.util.Objects;

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

    public static HttpHeader of(String type, String content) {
        HttpHeaderType httpHeaderType = HttpHeaderType.from(type);
        return new HttpHeader(httpHeaderType.getType(), content);
    }

    public static HttpHeader of(HttpHeaderType httpHeaderType) {
        String[] lines = httpHeaderType.getType().split(HEADER_DELIMITER);
        return new HttpHeader(lines[HEADER_TYPE_INDEX], null);
    }

    public String getHttpHeaderString() {
        return type + HEADER_DELIMITER + content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpHeader httpHeader = (HttpHeader) o;
        return Objects.equals(type, httpHeader.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
