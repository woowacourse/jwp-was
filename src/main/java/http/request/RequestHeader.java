package http.request;

import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    public static final String ACCEPT_KEY = "Accept";

    private final Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public String get(String key) {
        return header.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestHeader that = (RequestHeader) o;
        return Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }
}
