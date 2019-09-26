package http.session;

public interface SessionManager {
    HttpSession newSession();

    HttpSession getSession(String id);

    boolean validate(String id);
}
