package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaderFields {

    private final Map<String, String> fields = new HashMap<>();

    public void addField(String name, String value) {
        fields.put(name, value);
    }
}
