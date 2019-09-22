package http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHeader {
    private Map<String, String> fields;

    public HttpRequestHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public HttpRequestHeader(List<String> headers) {
        this.fields = new HashMap<>();

        headers.forEach(header -> {
            String[] keyValue = header.split(": ");
            this.fields.put(keyValue[0], keyValue[1]);
        });
    }

    public int getContentLength() {
        int length = 0;

        if (fields.containsKey("Content-Length")) {
            length = Integer.parseInt(fields.get("Content-Length"));
        }

        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestHeader that = (HttpRequestHeader) o;
        return fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
