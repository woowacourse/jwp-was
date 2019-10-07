package webserver.router;

import webserver.BadRequestException;
import webserver.pageprovider.PageProvider;

import java.util.ArrayList;
import java.util.List;

public class BasicRouter implements Router {

    private List<PredicatorPageProviderMatch> matches;

    BasicRouter(List<PredicatorPageProviderMatch> matches) {
        this.matches = matches;
    }

    public static BasicRouter getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    public BasicRouter addPageProvider(RouterPredicator predicator, PageProvider pageProvider) {
        matches.add(PredicatorPageProviderMatch.from(predicator, pageProvider));

        return this;
    }

    @Override
    public PageProvider retrieve(String pattern) {
        return matches.stream()
                .filter(match -> match.getPredicator().canHandle(pattern))
                .map(match -> match.getPageProvider())
                .findFirst()
                .orElseThrow(() -> new BadRequestException(pattern));
    }

    @Override
    public boolean canHandle(String pattern) {
        long numMatched = matches.stream()
                .filter(match -> match.isMatched(pattern))
                .count();

        return 0 < numMatched;
    }

    private static class BillPughSingleton {
        private static final BasicRouter INSTANCE = new BasicRouter(new ArrayList<>());
    }
}

