package http.common;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeader {

    private final Map<String, String> httpHeader = new HashMap<>();

    public HttpHeader(List<String> header) {
        parse(header);
    }

    private void parse(List<String> lines) {
        lines.forEach(line -> {
            String[] tokens = line.split(": ");
            httpHeader.put(tokens[0], tokens[1]);
        });
    }

    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        String value = httpHeader.get(key);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException();
        }

        return value;
    }

    public String serialize() {
        return httpHeader.entrySet().stream()
                .map(entry -> String.format("%s: %s\r\n", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining());
    }
}
