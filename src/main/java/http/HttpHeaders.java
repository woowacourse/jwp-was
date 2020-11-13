package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    public static final String DELIMITER = ": ";
    public static final int HEADER_TYPE_INDEX = 0;
    public static final int HEADER_VALUE_INDEX = 1;
    private final Map<HeaderType, String> httpHeaders;

    public HttpHeaders(Map<HeaderType, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaders from(BufferedReader br) throws IOException {
        return new HttpHeaders(extract(br));
    }

    private static Map<HeaderType, String> extract(BufferedReader br) throws IOException {
        Map<HeaderType, String> httpHeaders = new HashMap<>();
        String line = br.readLine();
        while ((line != null) && !"".equals(line)) {
            String[] tokens = line.split(DELIMITER);
            httpHeaders.put(HeaderType.from(tokens[HEADER_TYPE_INDEX]), tokens[HEADER_VALUE_INDEX]);
            line = br.readLine();
        }
        return httpHeaders;
    }

    public void addHeader(HeaderType headerType, String value) {
        httpHeaders.put(headerType, value);
    }

    public String get(HeaderType headerType) {
        return httpHeaders.get(headerType);
    }

    public Set<HeaderType> keySet() {
        return Collections.unmodifiableSet(httpHeaders.keySet());
    }
}
