package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    public static final String DELIMITER = ": ";
    public static final int HEADER_TYPE_INDEX = 0;
    public static final int HEADER_VALUE_INDEX = 1;
    private final Map<String, String> httpHeaders;

    public HttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static HttpHeaders from(BufferedReader br) throws IOException {
        return new HttpHeaders(extract(br));
    }

    private static Map<String, String> extract(BufferedReader br) throws IOException {
        Map<String, String> httpHeaders = new HashMap<>();
        String line = br.readLine();
        while ((line != null) && !"".equals(line)) {
            String[] tokens = line.split(DELIMITER);
            System.out.println(Arrays.toString(tokens));
            httpHeaders.put(tokens[HEADER_TYPE_INDEX], tokens[HEADER_VALUE_INDEX]);
            line = br.readLine();
        }
        return httpHeaders;
    }

    public void addHeader(String headerType, String value) {
        httpHeaders.put(headerType, value);
    }

    public String get(String headerType) {
        return httpHeaders.get(headerType);
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(httpHeaders.keySet());
    }
}
