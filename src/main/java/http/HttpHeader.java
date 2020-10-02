package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> httpHeaders;

    public HttpHeader(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public HttpHeader() {
        httpHeaders = new HashMap<>();
    }

    public String getHeader(String key) {
        return httpHeaders.get(key);
    }

    public void setHeader(String key, String value) {
        httpHeaders.put(key, value);
    }

    public Integer getContentLength() {
        if (httpHeaders.get(Header.CONTENT_TYPE.getName()) == null) {
            return 0;
        }
        return Integer.parseInt(httpHeaders.get(Header.CONTENT_LENGTH.getName()));
    }

    public String getContentType() {
        return httpHeaders.get(Header.CONTENT_TYPE.getName());
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
