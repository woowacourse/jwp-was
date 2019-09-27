package webserver.http.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {
    private final String id;
    private final Map<String, Object> session;

    public HttpSession(final String id) {
        this.id = id;
        this.session = new HashMap<>();
    }

    public void setAttribute(final String key, final String value) {
        session.put(key, value);
    }

    public Object getAttribute(final String key) {
        return session.get(key);
    }

    public void removeAttribute(final String key) {
        session.remove(key);
    }

    public void invalidate() {
        session.clear();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, session);
    }
}
