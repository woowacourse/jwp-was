package kr.wootecat.dongle.http.session;

public class SessionFactory {

    private SessionFactory() {
    }

    public static Session createSession(String sessionId) {
        return HttpSession.ofEmpty(sessionId);
    }
}
