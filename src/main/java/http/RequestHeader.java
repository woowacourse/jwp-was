package http;

import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    private final Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
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
