package http.session.support;

import java.util.UUID;

public interface SessionKeyGenerator {
    UUID generate(String id);
}
