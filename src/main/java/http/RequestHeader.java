package http;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> requestHeaders;

    public RequestHeader() {
        this.requestHeaders = new HashMap<>();
    }

    public void put(String key, String value){
        requestHeaders.put(key, value);
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
