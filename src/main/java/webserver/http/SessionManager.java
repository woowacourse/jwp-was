package webserver.http;

public interface SessionManager {
    HttpSession getSession(final String sessionId);

    HttpSession getSession();
}
