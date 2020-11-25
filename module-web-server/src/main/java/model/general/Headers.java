package model.general;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Headers {

    private Map<Header, String> headers;

    public Headers() {
        this(new HashMap<>());
    }

    public Headers(Map<Header, String> headers) {
        this.headers = headers;
    }

    public void addHeader(Header header, String value) {
        headers.put(header, value);
    }

    public boolean hasKey(Header header) {
        return headers.containsKey(header);
    }

    public String getValue(Header header) {
        return headers.get(header);
    }

    public Map<Header, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Headers otherHeaders = (Headers) o;
        return Objects.equals(headers, otherHeaders.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
