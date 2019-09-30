package webserver;

import http.Cookie;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;

import static com.google.common.net.HttpHeaders.COOKIE;

public class SessionInitiator {
    public void handle(HttpRequest request, HttpResponse response) {
        if (request.getHeader(COOKIE) == null || request.getSessionId() == null) {
            HttpSession session = SessionManager.createEmptySession();
            SessionManager.addSession(session);
            Cookie cookie = new Cookie("SESSIONID", session.getId());
            response.addCookie(cookie);
        }
    }
}
