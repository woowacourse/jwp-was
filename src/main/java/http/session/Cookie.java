package http.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final Logger logger = LoggerFactory.getLogger(Cookie.class);

    private static final String ATTRIBUTE_DELIMITER = ";\\s*";
    private static final String NAME_AND_VALUE_DELIMITER = "=";
    private static final int ATTRIBUTE_NAME = 0;
    private static final int VALUE = 1;
    private static final String EMPTY = "";

    private Map<String, String> attributes;

    private Cookie(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static Cookie getEmptyCookie() {
        return parse(EMPTY);
    }

    public static Cookie parse(String cookieHeader) {
        HashMap<String, String> attributes = new HashMap<>();

        if (isEmptyCookieHeader(cookieHeader)) {
            return new Cookie(attributes);
        }

        for (String attribute : cookieHeader.split(ATTRIBUTE_DELIMITER)) {
            addAttribute(attributes, attribute);
        }
        return new Cookie(attributes);
    }

    private static boolean isEmptyCookieHeader(String cookieLine) {
        return cookieLine == null || cookieLine.trim().isEmpty();
    }

    private static void addAttribute(HashMap<String, String> attributes, String attribute) {
        String[] nameAndValue = attribute.split(NAME_AND_VALUE_DELIMITER);

        try {
            attributes.put(nameAndValue[ATTRIBUTE_NAME], nameAndValue[VALUE]);
        } catch (IndexOutOfBoundsException e) {
            // TODO: 2019-10-12 예외 처리
            logger.error(e.getMessage(), e);
        }
    }

    // TODO: 2019-10-20 테스트
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
