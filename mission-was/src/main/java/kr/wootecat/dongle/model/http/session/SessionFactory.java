package kr.wootecat.dongle.model.http.session;

public class SessionFactory {

    private SessionFactory() {
    }

    public static Session createSession(String sessionId) {
        return HttpSession.ofEmpty(sessionId);
    }
}
