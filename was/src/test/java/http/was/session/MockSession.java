package http.was.session;

import java.util.Map;

import http.was.http.session.HttpSession;

public class MockSession implements HttpSession {

    private Map<String, Object> attributes;
    private String id;

    public MockSession(Map<String, Object> attributes, String id) {
        this.attributes = attributes;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    @Override
    public void invalidate() {
        this.attributes.clear();
    }
}
