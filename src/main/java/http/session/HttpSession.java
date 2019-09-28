package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSession {
    private final UUID id;
    private final Map<String, Object> attributes = new HashMap<>();

    public static HttpSession newInstance(UUID id) {
        return new HttpSession(id);
    }

    public HttpSession(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        HttpSession that = (HttpSession) o;
        return (id.compareTo(that.id) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
