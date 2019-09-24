package webserver.router;

import lombok.Getter;
import webserver.controller.Controller;

@Getter
public class PredicatorControllerMatch {
    private final ControllerPredicator predicator;
    private final Controller controller;

    private PredicatorControllerMatch(ControllerPredicator predicator, Controller controller) {
        this.predicator = predicator;
        this.controller = controller;
    }

    public static PredicatorControllerMatch from(ControllerPredicator predicator, Controller controller) {
        return new PredicatorControllerMatch(predicator, controller);
    }

    public boolean isMatched(String pattern) {
        return predicator.canHandle(pattern);
    }
}
