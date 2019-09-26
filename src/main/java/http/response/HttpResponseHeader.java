package http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpResponseHeader {
    private Map<String, String> fields = new HashMap<>();

    public void addHeader(String key, String value) {
        fields.put(key, value);
    }

    public String getHeader(String key) {
        return Optional.ofNullable(this.fields.get(key)).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
