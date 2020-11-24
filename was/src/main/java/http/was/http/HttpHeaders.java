package http.was.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import http.was.http.request.Cookies;

public class HttpHeaders {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

    private Map<String, String> httpHeaders;

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

    public void write(DataOutputStream dataOutputStream, Cookies cookies) throws IOException {
        httpHeaders.put(SET_COOKIE, cookies.toString());
        for (Map.Entry<String, String> key : httpHeaders.entrySet()) {
            dataOutputStream.writeBytes(key.getKey() + ": " + key.getValue() + " " + System.lineSeparator());
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }
}
