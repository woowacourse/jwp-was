package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaderFields {

    private final Map<String, String> fields = new HashMap<>();

    public void addField(String name, String value) {
        fields.put(name, value);
    }

    public String findField(final String name) {
        return fields.get(name);
    }
}
