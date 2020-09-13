package webserver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class RequestBody {
    private final Map<String, String> attribute;

    private RequestBody(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public static RequestBody of(String line) {
        Map<String, String> attributes = getBody(line);

        return new RequestBody(attributes);
    }

    private static Map<String, String> getBody(String line) {
        Map<String, String> attributes = new LinkedHashMap<>();
        if (Objects.isNull(line) || line.isEmpty()) {
            return attributes;
        }
        try {
            String[] params = line.split("&");
            for (String param : params) {
                String[] attribute = param.split("=");
                attributes.put(attribute[0], attribute[1]);
            }
            return attributes;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidRequestBodyException();
        }
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }
}
