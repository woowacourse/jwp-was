package http.common;

import java.util.List;
import java.util.Map;

public class Cookie {

    private static final String ATTRIBUTE_TOKENS_DELIMITER = "=";
    private static final String ATTRIBUTE_DELIMITER = ";";
    private static final String BLANK = " ";
    private final Map<String, String> attributeWithValue;
    private final List<String> attributeWithoutValue;

    public Cookie(final Map<String, String> attributeWithValue, final List<String> attributeWithoutValue) {
        this.attributeWithValue = attributeWithValue;
        this.attributeWithoutValue = attributeWithoutValue;
    }

    public String getAttributeValue(String attributeName) {
        return attributeWithValue.get(attributeName);
    }

    public Boolean hasContainsAttributeWithoutValue(String attributeWithoutValue) {
        return attributeWithoutValue.contains(attributeWithoutValue);
    }

    public String getCookieAttributeString() {
        StringBuilder stringBuilder = new StringBuilder();
        attributeWithValue.forEach((key, value) -> stringBuilder.append(key)
            .append(ATTRIBUTE_TOKENS_DELIMITER)
            .append(value)
            .append(ATTRIBUTE_DELIMITER)
            .append(BLANK));

        stringBuilder.append(String.join(ATTRIBUTE_DELIMITER, attributeWithoutValue));
        if (attributeWithValue.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(ATTRIBUTE_DELIMITER));
        }
        return stringBuilder.toString();
    }

    public boolean isLogined() {
        if (attributeWithValue.containsKey("logined")) {
            return "true".equals(attributeWithValue.get("logined"));
        }
        return false;
    }
}

