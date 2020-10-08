package jwp.was.webapplicationserver.configure.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessions {

    private static final HttpSessions INSTANCE = new HttpSessions();

    private Map<String, HttpSession> sessions = new HashMap<>();

    private HttpSessions() {
    }

    public static HttpSessions getInstance() {
        return INSTANCE;
    }

    public void saveSession(HttpSession httpSession) {
        sessions.put(httpSession.getId(), httpSession);
    }

    public HttpSession findSession(String id) {
        return sessions.get(id);
    }

    public void removeSession(String id) {
        sessions.remove(id);
    }
}
