package webserver;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private final Map<String, String> headerMap = new HashMap<>();

    public HttpHeader(List<String> header) {
        parse(header);
    }

    private void parse(List<String> header) {
        parseFirstLine(header.get(0));
        parseOtherLines(header.subList(1, header.size()));
    }

    private void parseOtherLines(List<String> lines) {
        lines.forEach(line -> {
            String[] tokens = line.split(": ");
            headerMap.put(tokens[0], tokens[1]);
        });
    }

    private void parseFirstLine(String line) {
        String[] firstLineTokens = line.split(" ");
        headerMap.put("method", firstLineTokens[0]);
        headerMap.put("url", firstLineTokens[1]);
        headerMap.put("version", firstLineTokens[2]);
    }

    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        String value = headerMap.get(key);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException();
        }

        return value;
    }
}
