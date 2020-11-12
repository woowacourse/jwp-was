package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {

    private String id;
    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }

    public void invalidate() {
        this.attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HttpSession))
            return false;
        HttpSession that = (HttpSession)o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "HttpSession{" +
            "id='" + id + '\'' +
            ", attributes=" + attributes +
            '}';
    }
}
