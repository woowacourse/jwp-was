package http.session;

public interface SessionManager {
    HttpSession getSession();

    HttpSession getSession(String id);
}
