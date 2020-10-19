package webserver.http.session;

import exception.InvalidSessionAttributeException;
import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String sessionId;
    private final Map<String, Object> attributes;

    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return sessionId;
    }

    public void setAttribute(String name, Object value) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidSessionAttributeException(name));

        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidSessionAttributeException(name));

        return this.attributes.get(name);
    }

    public void removeAttribute(String name) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidSessionAttributeException(name));

        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }
}
