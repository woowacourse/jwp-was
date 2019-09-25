package http.common;

import java.util.List;
import java.util.Map;

public class Cookie {

    private static final String ATTRIBUTE_TOKENS_DELIMITER = "=";
    private static final String ATTRIBUTE_DELIMITER = ";";
    private final Map<String, String> attributeWithValue;
    private final List<String> attributeWithoutValue;


    public Cookie(final Map<String, String> attributeWithValue, final List<String> attributeWithoutValue) {
        this.attributeWithValue = attributeWithValue;
        this.attributeWithoutValue = attributeWithoutValue;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        attributeWithValue.forEach((key, value) -> {
            stringBuilder.append(key).append(ATTRIBUTE_TOKENS_DELIMITER).append(value).append(ATTRIBUTE_DELIMITER);
        });
        stringBuilder.append(String.join(";", attributeWithoutValue));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(";"));
        return stringBuilder.toString();
    }
}

