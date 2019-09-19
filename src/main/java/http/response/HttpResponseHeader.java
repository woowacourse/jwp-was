package http.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private Map<String, String> fields;

    public HttpResponseHeader(final String headers) {
        this.fields = new HashMap<>();
        String[] field = headers.split("\n");

        for (String s : field) {
            String[] keyValue = s.split(": ");
            this.fields.put(keyValue[0], keyValue[1]);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
