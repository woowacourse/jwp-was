package webserver.request;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import exception.InvalidRequestBodyException;

public class RequestBody {
    private static final String REQUEST_BODY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

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
            String[] params = line.split(REQUEST_BODY_DELIMITER);
            for (String param : params) {
                String[] attribute = param.split(KEY_VALUE_DELIMITER);
                attributes.put(attribute[KEY_INDEX], attribute[VALUE_INDEX]);
            }
            return attributes;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidRequestBodyException();
        }
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }
}
