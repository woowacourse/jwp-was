package webserver;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import exception.InvalidRequestBodyException;

public class RequestBody {
    private final Map<String, String> attributes;

    private RequestBody(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static RequestBody of(String line) {
        Map<String, String> attributes = parseBodyAttributes(line);

        return new RequestBody(attributes);
    }

    private static Map<String, String> parseBodyAttributes(String line) {
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

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
