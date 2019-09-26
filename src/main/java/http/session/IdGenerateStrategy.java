package http.session;

@FunctionalInterface
public interface IdGenerateStrategy {
    String generate();
}
