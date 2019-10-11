package webserver.session;

import webserver.session.exception.InvalidatedSessionException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class HttpSession {
    private final String id;
    private boolean isInvalidate = FALSE;
    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public void setAttribute(String name, Object value) {
        checkInvalidated();
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        checkInvalidated();
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        checkInvalidated();
        attributes.remove(name);
    }

    private void checkInvalidated() {
        if (isInvalidate) {
            throw new InvalidatedSessionException();
        }
    }

    public void invalidate() {
        attributes = Collections.emptyMap();
        isInvalidate = TRUE;
    }

    public String getId() {
        return id;
    }
}
