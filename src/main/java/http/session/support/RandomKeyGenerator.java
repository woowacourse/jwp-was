package http.session.support;

import java.util.UUID;

public class RandomKeyGenerator implements SessionKeyGenerator {

    @Override
    public UUID generate(final String id) {
        if (id == null) {
            return UUID.randomUUID();
        }
        return UUID.fromString(id);
    }
}
