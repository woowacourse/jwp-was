package webserver.router;

import webserver.BadRequestException;
import webserver.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class OrderedRouter implements Router {
    private List<Router> orderedRouters;

    OrderedRouter(List<Router> orderedRouters) {
        this.orderedRouters = orderedRouters;
    }

    private static class BillPughSingleton {
        private static final OrderedRouter INSTANCE = new OrderedRouter(new ArrayList<>());
    }

    public static OrderedRouter getInstance() {
        return OrderedRouter.BillPughSingleton.INSTANCE;
    }

    public void pushBack(Router router) {
        orderedRouters.add(router);
    }

    @Override
    public Controller retrieveController(String pattern) {
        for (Router router : orderedRouters) {
            if (router.canHandle(pattern)) {
                return router.retrieveController(pattern);
            }
        }

        throw BadRequestException.ofPattern(pattern);
    }

    @Override
    public boolean canHandle(String pattern) {
        long numCanHandle = orderedRouters.stream()
                .filter(router -> router.canHandle(pattern))
                .count();

        return 0 < numCanHandle;
    }
}
