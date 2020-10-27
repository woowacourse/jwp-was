package servlet;

public interface HttpSession {

    public String getId();

    void setAttribute(String name, Object value);

    Object getAttribute(String name);

    void removeAttribute(String name);

    void invalidate();
}
