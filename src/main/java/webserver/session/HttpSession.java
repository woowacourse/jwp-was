package webserver.session;

import java.util.Map;

public class HttpSession {
    private final String id;
    private Map<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
    }

}
