package web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";
    public static final String HEADER_DELIMITER = ": ";

    private final Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeaderByKey(String key) {
        return headers.get(key);
    }

    public String getAcceptType() {
        String acceptInfo = headers.get(ACCEPT);

        return acceptInfo.split(",")[0];
    }

    public int getContentLength() {
        String contentLength = headers.get(CONTENT_LENGTH);
        return Integer.parseInt(contentLength);
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            dataOutputStream.writeBytes(key + HEADER_DELIMITER + value + " \r\n");
        }
        dataOutputStream.writeBytes("\n");
    }
}
