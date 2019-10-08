package webserver.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Session {
    public static final String COOKIE_IDENTIFIER = "DONUTSESSIONID";

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();
    private boolean isValid = true;

    protected Session(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public Session setAttribute(String name, Object value) {
        if (isValid) {
            this.attributes.put(name, value);
        }
        return this;
    }

    public Object getAttribute(String name) {
        return isValid ? this.attributes.get(name) : null;
    }

    public Object removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    public Session invalidate() {
        this.attributes.clear();
        isValid = false;
        return this;
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