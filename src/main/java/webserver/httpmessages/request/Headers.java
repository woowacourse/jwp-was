package webserver.httpmessages.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Headers {

    private static final String NAME_AND_VALUE_SEPARATOR = ": ";

    private Map<String, String> headers = new HashMap<>();

    Headers(List<String> headerLines) {
        headerLines.forEach(header -> {
            String[] split = header.split(NAME_AND_VALUE_SEPARATOR);
            System.out.println(split[0]);
            headers.put(split[0], split[1]);
        });
    }

    String getValue(String headerFiledName) {
        if (!headers.containsKey(headerFiledName)) {
            throw new IllegalArgumentException("this header field does not exist.");
        }
        return headers.get(headerFiledName);
    }
}
