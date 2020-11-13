package http.session;

public interface SessionManager {

    HttpSession generateSession();

    HttpSession getSession(String id);

    boolean validate(String id);
}
