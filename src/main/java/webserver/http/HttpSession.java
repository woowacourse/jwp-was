package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private String JSESSIONID;
    private Map<String, Object> attribute = new HashMap<>();

    public HttpSession() {
        JSESSIONID = UUID.randomUUID().toString();
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalid() {
        attribute.clear();
    }

}
