package jwp.was.webapplicationserver.configure.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSessions {

    public static final String ATTRIBUTE_KEY_USER = "USER";
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

    public void removeSession(String id) {
        sessions.remove(id);
    }

    public boolean existsUser(String sessionId) {
        HttpSession session = sessions.get(sessionId);
        return Objects.nonNull(session)
            && Objects.nonNull(session.getAttribute(ATTRIBUTE_KEY_USER));
    }
}
