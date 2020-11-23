package web.session;

public interface HttpSession {
    void setAttribute(String name, Object value);

    Object getAttribute(String name);

    void removeAttribute(String name);

    String getId();
}
