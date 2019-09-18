package webserver;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {

    private final Map<String, String> requestHeader = new HashMap<>();

    public RequestHeader(List<String> header) {
        parse(header);
    }

    private void parse(List<String> lines) {
        lines.forEach(line -> {
            String[] tokens = line.split(": ");
            requestHeader.put(tokens[0], tokens[1]);
        });
    }

    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        String value = requestHeader.get(key);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException();
        }

        return value;
    }
}
