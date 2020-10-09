package jwp.was.webapplicationserver.configure.security;

import java.util.Objects;
import jwp.was.webapplicationserver.configure.session.HttpSession;
import jwp.was.webapplicationserver.configure.session.HttpSessions;

public class Cookie {

    public static final String SET_COOKIE_SESSION_ID_KEY = "sessionId=";
    public static final String ATTRIBUTE_KEY_USER = "USER";
    private static final String COOKIE_DELIMITER = ";";
    private static final HttpSessions HTTP_SESSIONS = HttpSessions.getInstance();

    private final String cookie;

    public Cookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean verifySessionId() {
        return Objects.nonNull(extractSessionId());
    }

    private String extractSessionId() {
        if (Objects.isNull(cookie)) {
            return null;
        }
        String sessionId = getSessionId();
        if (Objects.isNull(sessionId) || sessionId.isEmpty() || notExistsUser(sessionId)) {
            return null;
        }
        return sessionId;
    }

    private String getSessionId() {
        String[] splitCookie = cookie.split(COOKIE_DELIMITER);
        for (String cookie : splitCookie) {
            if (cookie.startsWith(SET_COOKIE_SESSION_ID_KEY)) {
                return cookie.substring(SET_COOKIE_SESSION_ID_KEY.length());
            }
        }
        return null;
    }

    private boolean notExistsUser(String sessionId) {
        HttpSession session = HTTP_SESSIONS.findSession(sessionId);
        return Objects.isNull(session) || Objects.isNull(session.getAttribute(ATTRIBUTE_KEY_USER));
    }
}
