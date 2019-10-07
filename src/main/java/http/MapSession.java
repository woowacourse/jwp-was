package http;

import http.response.SessionRemover;

import java.util.HashMap;
import java.util.Map;

public class MapSession extends AbstractSession {
    private final Map<String, Object> attributes;

    private MapSession(String id, Map<String, Object> attributes, SessionRemover remover) {
        super(id, remover);
        this.attributes = attributes;
    }

    public static MapSession of(String id, SessionRemover remover) {
        return new MapSession(id, new HashMap<>(), remover);
    }

    @Override
    void _setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    Object _getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    void _removeAttribute(String name) {
        attributes.remove(name);
    }
}
