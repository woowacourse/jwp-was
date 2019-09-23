package webserver.router;

import webserver.BadRequestException;
import webserver.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class Router {

    private List<PredicatorControllerMatch> matches;

    Router(List<PredicatorControllerMatch> matches) {
        this.matches = matches;
    }

    private static class BillPughSingleton {
        private static final Router INSTANCE = new Router(new ArrayList<>());
    }

    public static Router getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    public Router addController(ControllerPredicator predicator, Controller controller) {
        matches.add(new PredicatorControllerMatch(predicator, controller));

        return this;
    }

    public Controller retrieveController(String pattern) {
        return matches.stream()
                .filter(match -> match.predicator.canHandle(pattern))
                .map(match -> match.controller)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(pattern));
    }

    static class PredicatorControllerMatch {
        public ControllerPredicator predicator;
        public Controller controller;

        public PredicatorControllerMatch(ControllerPredicator predicator, Controller controller) {
            this.predicator = predicator;
            this.controller = controller;
        }
    }
}
