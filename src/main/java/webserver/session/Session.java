package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session {
    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    protected Session(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Object removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }

    @Override
    public String toString() {
        return "Session(" + id + "): {"
                + this.attributes.entrySet().stream()
                                            .map(x -> x.getKey() + "=" + x.getValue())
                                            .reduce((a, b) -> a + "; " + b)
                                            .orElse("") + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        final Session rhs = (Session) o;
        return Objects.equals(this.id, rhs.id) &&
                Objects.equals(this.attributes, rhs.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.attributes);
    }
}