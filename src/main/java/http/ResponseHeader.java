package http;

import java.util.*;

import static http.HttpResponse.NEW_LINE;

public class ResponseHeader {
    private final Map<String, String> headers = new HashMap<>();

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String readHeaders() {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> keys = new ArrayList<>(this.headers.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            stringBuilder.append(key + ": " + this.headers.get(key) + NEW_LINE);
        }

        return stringBuilder.toString();
    }
}
