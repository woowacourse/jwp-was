package http.session;

public class FixedSessionKeyStrategy implements SessionKeyGenerator {
    @Override
    public String create() {
        return "FixedSessionKey";
    }
}
