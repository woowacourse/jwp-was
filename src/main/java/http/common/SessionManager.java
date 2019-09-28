package http.common;

import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final int ONE_DAY = 60 * 60 * 24;
    private static final String JSESSIONID = "JSESSIONID";
    private static final String PATH_ROOT = "/";

    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessions.put(sessionId, httpSession);
        return httpSession;
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void sendSession(HttpResponse httpResponse, HttpSession httpSession) {
        Cookie cookie = new Cookie(JSESSIONID, httpSession.getId());
        cookie.setMaxAge(ONE_DAY);
        cookie.setPath(PATH_ROOT);
        httpResponse.addCookie(cookie);
    }
}
