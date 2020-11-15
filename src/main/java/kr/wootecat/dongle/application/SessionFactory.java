package kr.wootecat.dongle.application;

public class SessionFactory {
    public static Session createSession(String sessionId) {
        return HttpSession.ofEmpty(sessionId);
    }
}
