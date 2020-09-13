package webserver;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> attribute;

    public RequestBody(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public static RequestBody of(String line) {
        try {
            Map<String, String> attributes = getBody(line);

            return new RequestBody(attributes);
        } catch (Exception e) {
            throw new InvalidRequestBodyException();
        }
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    private static Map<String, String> getBody(String line) {
        Map<String, String> attributes = new LinkedHashMap<>();
        if (line.isEmpty()) {
            return attributes;
        }
        String[] params = line.split("&");
        for (String param : params) {
            String[] attribute = param.split("=");
            attributes.put(attribute[0], attribute[1]);
        }
        return attributes;
    }
}
