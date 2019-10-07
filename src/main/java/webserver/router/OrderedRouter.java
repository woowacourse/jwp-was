package webserver.router;

import webserver.BadRequestException;
import webserver.pageprovider.PageProvider;

import java.util.ArrayList;
import java.util.List;

public class OrderedRouter implements Router {
    private List<Router> orderedRouters;

    OrderedRouter(List<Router> orderedRouters) {
        this.orderedRouters = orderedRouters;
    }

    public static OrderedRouter getInstance() {
        return OrderedRouter.BillPughSingleton.INSTANCE;
    }

    public void pushBack(Router router) {
        orderedRouters.add(router);
    }

    @Override
    public PageProvider retrieve(String pattern) {
        for (Router router : orderedRouters) {
            if (router.canHandle(pattern)) {
                return router.retrieve(pattern);
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

    private static class BillPughSingleton {
        private static final OrderedRouter INSTANCE = new OrderedRouter(new ArrayList<>());
    }
}
