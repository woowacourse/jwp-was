package http.session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cookie {
    private static final String ATTRIBUTE_DELIMITER = ";\\s*";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int SPLICED_ATTRIBUTE_SIZE = 2;
    private static final String EMPTY = "";

    private Map<String, String> attributes;

    private Cookie(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static Cookie getEmptyCookie() {
        return parse(EMPTY);
    }

    public static Cookie parse(String cookieString) {
        HashMap<String, String> attributes = new HashMap<>();

        if (Objects.nonNull(cookieString)) {
            Arrays.stream(cookieString.split(ATTRIBUTE_DELIMITER))
                    .map(attribute -> attribute.split(KEY_VALUE_DELIMITER))
                    .filter(spliced -> spliced.length == SPLICED_ATTRIBUTE_SIZE)
                    .forEach(spliced -> attributes.put(spliced[KEY], spliced[VALUE]));
        }
        return new Cookie(attributes);
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }
}
