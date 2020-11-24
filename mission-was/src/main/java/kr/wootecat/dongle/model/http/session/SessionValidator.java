package kr.wootecat.dongle.model.http.session;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public class SessionValidator {

    private static final String COOKIE_KEY_SESSION_ID = "JSESSIONID";

    private final SessionStorage sessionStorage;
    private final IdGenerator idGenerator;

    public SessionValidator(SessionStorage sessionStorage, IdGenerator idGenerator) {
        this.sessionStorage = sessionStorage;
        this.idGenerator = idGenerator;
    }

    public void checkRequestSession(HttpRequest request, HttpResponse response) {
        if (!request.hasCookie(COOKIE_KEY_SESSION_ID)) {
            String sessionId = idGenerator.create();
            sessionStorage.insert(sessionId);
            Cookie sessionIdCookie = new Cookie(COOKIE_KEY_SESSION_ID, sessionId, "/");
            response.addCookie(sessionIdCookie);
        }
    }
}
