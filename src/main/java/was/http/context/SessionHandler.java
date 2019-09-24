package was.http.context;

import java.util.UUID;

public interface SessionHandler {
    void addSession(UUID sessionId);
    Session getSession(UUID sessionId);
    void invalidate(UUID sessionId);
}
