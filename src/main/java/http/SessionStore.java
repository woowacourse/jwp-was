package http;

import http.request.Request;
import http.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStore {
    private static String JSESSION = "JSESSION";

    private static Map<String, Session> sessions = new HashMap<>();

    public static void setSession(Request request, Response response) {
        Cookie requestCookie = request.getCookie(JSESSION);

        if (haveSession(requestCookie)) {
            maintainSession(request, requestCookie);
        }

        if (haveNotSession(requestCookie)) {
            makeSession(request, response, requestCookie);
        }
    }

    private static boolean haveSession(Cookie requestCookie) {
        return requestCookie != null
                && sessions.containsKey(requestCookie.getValue());
    }

    private static boolean haveNotSession(Cookie requestCookie) {
        return requestCookie == null
                || !sessions.containsKey(requestCookie.getValue());
    }

    private static void makeSession(Request request, Response response, Cookie requestCookie) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId);

        sessions.put(sessionId, session);
        request.setSession(session);
        response.addCookie(new Cookie(JSESSION, sessionId));
    }

    private static void maintainSession(Request request, Cookie requestCookie) {
        request.setSession(sessions.get(requestCookie.getValue()));
    }
}
