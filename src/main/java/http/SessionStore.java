package http;

import http.request.Request;
import http.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStore {
    private static Map<String, Session> sessions = new HashMap<>();

    public static void addSessions(String sessionId, Session session) {
        sessions.put(sessionId, session);
    }

    public static void getSession(String sessionId) {
        sessions.get(sessionId);
    }

    public static int getSessionSize() {
        return sessions.size();
    }

    public static void setSession(Request request, Response response) {
        if (request.getCookie("JSESSION") == null || !sessions.containsKey(request.getCookie("JSESSION").getValue())) {
            System.out.println("하이");
            String sessionId = UUID.randomUUID().toString();
            Session session = new Session(sessionId);
            System.out.println(session);
            sessions.put(sessionId, session);
            request.setSession(session);
            response.addCookie(new Cookie("JSESSION", sessionId));
        }

        if (request.getCookie("JSESSION") != null && sessions.containsKey(request.getCookie("JSESSION").getValue())) {
            System.out.println("하하");
            request.setSession(sessions.get(request.getCookie("JSESSION").getValue()));
        }
    }
}
