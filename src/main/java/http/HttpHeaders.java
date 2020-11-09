package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    public static final String DELIMITER = ": ";
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
            httpHeaders.put(tokens[0].trim(), tokens[1].trim());
            line = br.readLine();
        }
        return httpHeaders;
    }

    public String get(String headerType) {
        return httpHeaders.get(headerType);
    }

}
