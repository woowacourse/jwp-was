package http;

import http.exception.NotFoundSessionAttributeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {
    private String id;
    private Map<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Object getAttributes(String key) {
        if (attributes.containsKey(key)) {
            return attributes.get(key);
        }

        throw new NotFoundSessionAttributeException();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        if (attributes.containsKey(name)) {
            attributes.remove(name);
            return;
        }

        throw new NotFoundSessionAttributeException();
    }

    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
