package webserver.router;

@FunctionalInterface
public interface RouterPredicator {
    boolean canHandle(String pattern);
}
