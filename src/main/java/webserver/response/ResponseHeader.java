package webserver.response;

import java.util.LinkedHashMap;
import java.util.Map;

import webserver.request.ServletRequest;

public class ResponseHeader {
    private final Map<String, String> headers;

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static ResponseHeader emptyHeader() {
        return new ResponseHeader(new LinkedHashMap<>());
    }

    public static ResponseHeader of(View view, ServletRequest request) {
        ResponseHeader responseHeader = emptyHeader();
        String contentType = request.getAccept();
        responseHeader.addHeader("Content-Type", contentType);
        if (view.isRedirect()) {
            responseHeader.addHeader("Location", "/" + view.getViewName());
        }

        return responseHeader;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, null);
    }
}
