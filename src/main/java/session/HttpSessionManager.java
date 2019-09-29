package session;

public interface HttpSessionManager {
    public HttpSession findSession(String sessionId);

    public HttpSession createSession();
}
