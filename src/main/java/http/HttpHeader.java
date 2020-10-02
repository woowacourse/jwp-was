package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> requestHeaders;

    public HttpHeader(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public HttpHeader() {
        requestHeaders = new HashMap<>();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public void setHeader(String key, String value) {
        requestHeaders.put(key, value);
    }

    public Integer getContentLength() {
        if (requestHeaders.get(Header.CONTENT_TYPE.getName()) == null) {
            return 0;
        }
        return Integer.parseInt(requestHeaders.get(Header.CONTENT_LENGTH.getName()));
    }

    public String getContentType() {
        return requestHeaders.get(Header.CONTENT_TYPE.getName());
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (String key : requestHeaders.keySet()) {
            dataOutputStream.writeBytes(key + ": " + requestHeaders.get(key) + " " + System.lineSeparator());
        }
        dataOutputStream.writeBytes(System.lineSeparator());
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
