package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private static final Logger log = LoggerFactory.getLogger(HttpResponseHeader.class);
    private Map<String, String> fields;

    public HttpResponseHeader(final String headers) {
        this.fields = new HashMap<>();
        String[] field = headers.split("\n");

        for (String s : field) {
            String[] keyValue = s.split(": ");
            if ("".equals(s)) return;
            this.fields.put(keyValue[0], keyValue[1]);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\n"));
        stringBuilder.append("\r\n").append("\r\n");

        return stringBuilder.toString();
    }
}
