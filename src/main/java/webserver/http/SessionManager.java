package webserver.http;

import java.util.Optional;

public interface SessionManager {
    Optional<HttpSession> getSession(final String sessionId);

    HttpSession createSession();
}
