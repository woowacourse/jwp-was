package request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Headers {

    private static final String NAME_AND_VALUE_SEPARATOR = ": ";

    private Map<String, String> headers = new HashMap<>();

    public Headers(List<String> headerLines) {
        headerLines.forEach(header -> {
            String[] split = header.split(NAME_AND_VALUE_SEPARATOR);
            if (split.length != 2) {
                throw new IllegalArgumentException("input header format : " + header + " is wrong.");
            }
            headers.put(split[0], split[1]);
        });
    }

    boolean doesHeaderExist(String headerFieldName) {
        return headers.containsKey(headerFieldName);
    }

    public String getValue(String headerFieldName) {
        if (!doesHeaderExist(headerFieldName)) {
            throw new IllegalArgumentException("this header field does not exist.");
        }
        return headers.get(headerFieldName);
    }
}
