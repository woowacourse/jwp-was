package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private Map<String, String> fields;

    public HttpRequestHeader(final String headers) {
        this.fields = new HashMap<>();

        String[] field = headers.split("\n");

        for (String s : field) {
            String[] keyValue = s.split(": ");
            this.fields.put(keyValue[0], keyValue[1]);
        }
    }

    public int getContentLength() {
        int length = 0;

        if (fields.containsKey("Content-Length")) {
            length = Integer.parseInt(fields.get("Content-Length"));
        }

        return length;
    }
}
