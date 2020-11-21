package http.session;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSession implements HttpSession {

    private Map<String, Object> attributes = new HashMap<>();
    private UUID id = UUID.randomUUID();

    public WebSession(User user) {
        this.attributes.put("email", user.getEmail());
    }

    @Override
    public String getId() {
        return this.id.toString();
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
