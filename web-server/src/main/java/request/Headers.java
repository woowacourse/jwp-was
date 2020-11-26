package request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Headers {

    static final String CONTENT_LENGTH = "Content-Length";
    private static final String NAME_AND_VALUE_SEPARATOR = ": ";

    private Map<String, String> headers = new HashMap<>();

    Headers(List<String> headerLines) {
        headerLines.forEach(header -> {
            String[] split = header.split(NAME_AND_VALUE_SEPARATOR);
            if (split.length != 2) {
                throw new IllegalArgumentException("input header format : " + header + " is wrong.");
            }
            headers.put(split[0], split[1]);
        });
    }

    boolean isExist(String headerName) {
        return headers.containsKey(headerName);
    }

    String getValue(String headerName) {
        if (!isExist(headerName)) {
            throw new IllegalArgumentException("this header field does not exist.");
        }
        return headers.get(headerName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> headerNames = headers.keySet();

        headerNames.forEach(headerName ->
            stringBuilder.append(headers.get(headerName))
                .append("\n")
        );

        return stringBuilder.toString();
    }
}
