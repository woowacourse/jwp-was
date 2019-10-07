package webserver.domain;

import java.util.*;

public class HttpSession {
    private static final Map<String, HttpSession> manager = new WeakHashMap<>();
    private static final String EMPTY = "";

    private final String uuid;
    private final Map<String, Object> attributes;

    public HttpSession() {
        uuid = UUID.randomUUID().toString();
        attributes = new HashMap<>();
    }

    public String getId() {
        return uuid;
    }

    public Object getAttribute(final String name) {
        return attributes.get(name);
    }

    public void setAttribute(final String name, final Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(final String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    public static HttpSession makeNew() {
        final HttpSession session = new HttpSession();
        set(session);
        return session;
    }

    public static HttpSession get(final String sessionId) {
        return manager.getOrDefault(sessionId, makeNew());
    }

    public static HttpSession set(HttpSession session) {
        if (Objects.isNull(session)) {
            session = new HttpSession();
        }
        manager.put(session.getId(), session);
        return session;
    }

    public static void remove(final HttpSession session) {
        remove(session.getId());
    }

    public static void remove(final String sessionId) {
        manager.remove(sessionId);
    }

    public static void clearAll() {
        manager.clear();
    }
}
