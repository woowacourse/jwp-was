package was.http.context;

import java.util.UUID;

public interface SessionHandler {
    Session addNewSession(UUID sessionId);
    Session getSession(UUID sessionId);
    void invalidate(UUID sessionId);
    boolean hasSession(UUID sessionId);
}
