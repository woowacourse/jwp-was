package http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private static final String DELIMITER = ": ";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";
    
    private Map<String, String> httpHeaders;

    public HttpHeaders(BufferedReader br) throws IOException {
        String line = br.readLine();
        Map<String, String> requestHeaders = new HashMap<>();
        while (line != null && !"".equals(line)) {
            String[] token = line.split(DELIMITER);
            requestHeaders.put(token[0], token[1]);
            line = br.readLine();
        }
        this.httpHeaders = requestHeaders;
    }

    public HttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public HttpHeaders() {
        this(new HashMap<>());
    }

    public String getHeader(String key) {
        return httpHeaders.get(key);
    }

    public void setHeader(String key, String value) {
        httpHeaders.put(key, value);
    }

    public Integer getContentLength() {
        if (httpHeaders.get(CONTENT_TYPE) == null) {
            return 0;
        }
        return Integer.parseInt(httpHeaders.get(CONTENT_LENGTH));
    }

    public String getContentType() {
        return httpHeaders.get(CONTENT_TYPE);
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (String key : httpHeaders.keySet()) {
            dataOutputStream.writeBytes(key + ": " + httpHeaders.get(key) + " " + System.lineSeparator());
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }
}
