package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseHeader.class);

    private Map<String, String> fields;

    public HttpResponseHeader() {
        fields = new HashMap<>();
    }

    public HttpResponseHeader(final String headers) {
        this.fields = new HashMap<>();
        String[] field = headers.split("\n");

        for (String s : field) {
            String[] keyValue = s.split(": ");
            this.fields.put(keyValue[0], keyValue[1]);
        }
    }

    public void addField(String key, String value) {
        fields.put(key, value);
    }

    public void setLocation(String uri) {
        logger.debug("redirect uri: {}", uri);
        fields.put("Location", uri);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
