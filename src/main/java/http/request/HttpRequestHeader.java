package http.request;

import java.util.Map;

public class HttpRequestHeader {

    private Map<String, String> requestHeader;

    public HttpRequestHeader(final Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getValue(String name) {
        return requestHeader.get(name);
    }
}
