package webserver.router;

import lombok.Getter;
import webserver.pageprovider.PageProvider;

@Getter
public class PredicatorPageProviderMatch {
    private final RouterPredicator predicator;
    private final PageProvider pageProvider;

    public PredicatorPageProviderMatch(RouterPredicator predicator, PageProvider pageProvider) {
        this.predicator = predicator;
        this.pageProvider = pageProvider;
    }


    public static PredicatorPageProviderMatch from(RouterPredicator predicator, PageProvider pageProvider) {
        return new PredicatorPageProviderMatch(predicator, pageProvider);
    }

    public boolean isMatched(String pattern) {
        return predicator.canHandle(pattern);
    }
}
