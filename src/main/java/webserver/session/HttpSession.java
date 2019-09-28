package webserver.session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    private HttpSession() {
        this.id = createRandomId();
    }

    public static HttpSession newInstance() {
        return new HttpSession();
    }

    private String createRandomId() {
        return UUID.randomUUID().toString() + new Date().getTime();
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(final String name, final Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }
}
