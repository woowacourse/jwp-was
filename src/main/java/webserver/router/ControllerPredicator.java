package webserver.router;

import java.util.function.Predicate;


@FunctionalInterface
public interface ControllerPredicator {
    boolean canHandle(String pattern);
}
