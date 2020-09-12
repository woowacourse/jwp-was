package http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private Map<String, String> requestHeader;

    public RequestHeader() {
        this.requestHeader = new HashMap<>();
    }

    public void add(String name, String value) {
        requestHeader.put(name, value);
    }

    public String getValue(String name) {
        return requestHeader.get(name);
    }
}
