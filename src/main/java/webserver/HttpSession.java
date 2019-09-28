package webserver;

import java.util.Map;

public class HttpSession {
    private final String id;
    private Map<String, Object> attributes;

    public HttpSession(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }
}
