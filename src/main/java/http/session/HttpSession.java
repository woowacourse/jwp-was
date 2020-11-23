package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpSession {
    private UUID id = UUID.randomUUID();
    private boolean isValid = true;
    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        this.isValid = false;
        attributes.clear();
    }

    public UUID getId() {
        return id;
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public String toString() {
        String sessionId = String.format("jsessionid=%s; ", id.toString());
        String attributes = this.attributes.entrySet()
                .stream()
                .filter(this::exclude)
                .map(this::parseAttribute)
                .collect(Collectors.joining("; "));
        return sessionId + attributes;
    }

    private boolean exclude(Map.Entry<String, Object> entry) {
        return !entry.getKey().equals("userId") && !entry.getValue().equals(false);
    }

    private String parseAttribute(Map.Entry<String, Object> entry) {
        if (entry.getValue().equals(true)) {
            return entry.getKey();
        }
        return String.format("%s=%s", entry.getKey(), entry.getValue().toString());
    }
}
