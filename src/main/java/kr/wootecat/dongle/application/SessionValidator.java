package kr.wootecat.dongle.application;

import kr.wootecat.dongle.application.http.Cookie;
import kr.wootecat.dongle.application.http.request.HttpRequest;
import kr.wootecat.dongle.application.http.response.HttpResponse;

public class SessionValidator {

    private final SessionStorage sessionStorage;
    private final IdGenerator idGenerator;

    public SessionValidator(SessionStorage sessionStorage, IdGenerator idGenerator) {
        this.sessionStorage = sessionStorage;
        this.idGenerator = idGenerator;
    }

    public void checkRequestSession(HttpRequest request, HttpResponse response) {
        if (!request.hasCookie("JSESSIONID")) {
            String sessionId = idGenerator.create();
            sessionStorage.insert(sessionId);
            Cookie sessionIdCookie = new Cookie("JSESSIONID", sessionId);
            sessionIdCookie.setPath("/");
            response.addCookie(sessionIdCookie);
        }
    }
}
