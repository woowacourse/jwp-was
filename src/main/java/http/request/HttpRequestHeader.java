package http.request;

import java.util.Map;

public class HttpRequestHeader {
    private Map<String, String> fields;

    public HttpRequestHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public int getContentLength() {
        int length = 0;

        if (fields.containsKey("Content-Length")) {
            length = Integer.parseInt(fields.get("Content-Length"));
        }

        return length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
