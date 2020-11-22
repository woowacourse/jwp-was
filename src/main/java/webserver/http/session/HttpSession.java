package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpSession {
    private final UUID id;
    private final Map<String, Object> attributes;
    private HttpSessionStatus httpSessionStatus;

    public HttpSession() {
        this.id = UUID.randomUUID();
        this.attributes = new HashMap<>();
        this.httpSessionStatus = HttpSessionStatus.VALID;
    }

    public UUID getId() {
        return this.id;
    }

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
        httpSessionStatus = HttpSessionStatus.INVALID;
        attributes.clear();
    }

    public String getHttpSessionString() {
        String sessionId = String.format("jsessionid=%s; ", id.toString());
        String sessionAttributes = attributes.entrySet().stream()
                .filter(this::excludeAttribute)
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("; "));
        return sessionId + sessionAttributes;
    }

    private boolean excludeAttribute(Map.Entry<String, Object> entry) {
        return !entry.getKey().equals("userId");
    }

    public boolean isValidSession() {
        return httpSessionStatus.isValid();
    }
}
