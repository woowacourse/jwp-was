package http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {
    private Map<String, String> headers;

    public static HttpHeaders from(String[] lines) {
        Map<String, String> headers = Arrays.stream(lines)
            .map(line -> line.split(": "))
            .collect(Collectors.toMap(
                pair -> pair[0], pair -> pair[1]
            ));
        return new HttpHeaders(headers);
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public int size() {
        return headers.size();
    }
}
