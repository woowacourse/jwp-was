package http.session;

@FunctionalInterface
public interface SessionKeyGenerator {
    String create();
}
