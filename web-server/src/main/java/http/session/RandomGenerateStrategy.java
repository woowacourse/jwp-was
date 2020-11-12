package http.session;

import java.util.UUID;

public class RandomGenerateStrategy implements SessionIdGenerateStrategy{

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
