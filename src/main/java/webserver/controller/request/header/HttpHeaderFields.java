package webserver.controller.request.header;

import java.util.Map;

public class HttpHeaderFields {
    private final Map<String, String> headerFields;

    public HttpHeaderFields(Map<String, String> headerFields) {
        this.headerFields = headerFields;
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.get("Content-Length"));
    }

    public String getHeaderFieldValue(String key) {
        return headerFields.get(key);
    }
}
