package http.request;

import java.util.Map;

public class HttpRequestHeader {

    private final Map<String, String> requestHeader;

    public HttpRequestHeader(final Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getValue(final String name) {
        return this.requestHeader.get(name);
    }
}
