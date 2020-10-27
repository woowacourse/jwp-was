package server.domain.request;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import web.server.domain.exception.AttributeNotFoundException;

@Getter
public class HttpSevletSession {

    private String id;
    private Map<String, Object> attribute;

    public HttpSevletSession(String id) {
        this.id = id;
        this.attribute = new HashMap<>();
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        if (attribute.containsKey(name)) {
            return attribute.get(name);
        }
        throw new AttributeNotFoundException(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }
}
