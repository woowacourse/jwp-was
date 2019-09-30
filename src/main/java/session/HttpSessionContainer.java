package session;

public interface HttpSessionContainer {
    public HttpSession findSession(String sessionId);

    public HttpSession createSession();
}
