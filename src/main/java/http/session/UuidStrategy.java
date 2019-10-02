package http.session;

import java.util.UUID;

public class UuidStrategy implements SessionIdStrategy {
    @Override
    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
