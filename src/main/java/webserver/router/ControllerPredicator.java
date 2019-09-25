package webserver.router;

@FunctionalInterface
public interface ControllerPredicator {
    boolean canHandle(String pattern);
}
