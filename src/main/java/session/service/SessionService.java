package session.service;

import session.db.SessionRepository;
import session.model.HttpSession;

public class SessionService {
    private static SessionService instance = new SessionService();

    private SessionService() {
    }

    public static SessionService getInstance() {
        return instance;
    }

    public void add(HttpSession httpSession) {
        SessionRepository.addSession(httpSession.getId(), httpSession);
    }

    public HttpSession findById(String id) {
        return SessionRepository.findById(id);
    }

    public void update(HttpSession httpSession) {
        SessionRepository.update(httpSession);
    }
}
