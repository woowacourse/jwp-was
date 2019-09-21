package http.session;

import java.util.UUID;

public class RandomGenerateStrategy implements IdGenerateStrategy {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
