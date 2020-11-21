package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestHeader {
    private static final String HEADER_DELIMITER = ":\\s";
    private static final String lineSeparator = System.lineSeparator();
    private static final String CONTENT_LENGTH = "content-length";
    private static final String COOKIE = "cookie";
    private static final String COOKIE_DELIMETER = ";";
    private static final String KEY_VALUE_DELIMETER = "=";

    private final Map<String, String> fields;
    private final Set<Map<String, String>> cookies;

    public RequestHeader(Map<String, String> fields, Set<Map<String, String>> cookies) {
        this.fields = fields;
        this.cookies = cookies;
    }

    public static RequestHeader of(BufferedReader bufferedReader) throws IOException {
        Map<String, String> fields = new HashMap<>();
        Set<Map<String, String>> cookies = new HashSet<>();

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            if (line.isEmpty()) {
                break;
            }

            String[] headerTokens = line.split(HEADER_DELIMITER);
            if (headerTokens.length >= 2) {
                String key = headerTokens[0].toLowerCase();
                String value = headerTokens[1];
                if (key.equals(COOKIE)) {
                    parseCookie(value, cookies);
                    break;
                }
                fields.put(key, value);
            }
        }

        return new RequestHeader(fields, cookies);
    }

    private static void parseCookie(String value, Set<Map<String, String>> cookies) {
        String[] splitValues = value.split(COOKIE_DELIMETER);

        Map<String, String> cookie = Arrays.stream(splitValues)
            .map(splitValue -> splitValue.split(KEY_VALUE_DELIMETER))
            .collect(Collectors.toMap(v -> v[0], v -> v[1]));
        cookies.add(cookie);
    }

    public String getCookieValue(String cookieName) {
        return cookies.stream()
            .map(cookie -> cookie.get(cookieName))
            .filter(Objects::nonNull)
            .findAny()
            .orElseGet(() -> "");
    }

    public String getContentLength() {
        return fields.get(CONTENT_LENGTH);
    }

    public String toValue() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            sb.append(String.format("%s: %s%s", field.getKey(), field.getValue(), lineSeparator));
        }

        return sb.toString();
    }
}