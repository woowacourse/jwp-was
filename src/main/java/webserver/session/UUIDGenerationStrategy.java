package webserver.session;

import java.util.UUID;

public class UUIDGenerationStrategy implements IdGenerationStrategy {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
