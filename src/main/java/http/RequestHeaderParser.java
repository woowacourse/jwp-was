package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeaderParser {

    public static RequestHeader parse(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();

        headerLines.stream().forEach(headerLine -> {
            String[] headerTokens = headerLine.split(":");
            String name = headerTokens[0].trim();
            String value = headerTokens[1].trim();
            headers.put(name, value);
        });
        return new RequestHeader(headers);
    }
}
