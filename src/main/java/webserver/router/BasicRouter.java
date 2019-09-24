package webserver.router;

import webserver.BadRequestException;
import webserver.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class BasicRouter implements Router {

    private List<PredicatorControllerMatch> matches;

    BasicRouter(List<PredicatorControllerMatch> matches) {
        this.matches = matches;
    }

    private static class BillPughSingleton {
        private static final BasicRouter INSTANCE = new BasicRouter(new ArrayList<>());
    }

    public static BasicRouter getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    public BasicRouter addController(ControllerPredicator predicator, Controller controller) {
        matches.add(PredicatorControllerMatch.from(predicator, controller));

        return this;
    }

    @Override
    public Controller retrieveController(String pattern) {
        return matches.stream()
                .filter(match -> match.getPredicator().canHandle(pattern))
                .map(match -> match.getController())
                .findFirst()
                .orElseThrow(() -> new BadRequestException(pattern));
    }

    @Override
    public boolean canHandle(String pattern) {
        long numMatched =  matches.stream()
                .filter(match -> match.isMatched(pattern))
                .count();

        return 0 < numMatched;
    }
}

