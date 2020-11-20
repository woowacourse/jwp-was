package domain.request;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class HttpSession {

    private String id;
    private Map<String, Object> attribute;

    public HttpSession(String id) {
        this.id = id;
        this.attribute = new HashMap<>();
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

    public void invalidate() {
        attribute.clear();
    }
}
