package webserver.request;

import java.util.Map;

public class RequestHeader {
    private final Map<String, String> headers;

    public RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public String putHeader(String headerKey, String headerValue) {
        return headers.put(headerKey, headerValue);
    }

}
