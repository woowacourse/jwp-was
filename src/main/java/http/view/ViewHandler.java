package http.view;

import http.model.HttpResponse;
import http.supoort.IllegalHttpRequestException;
import http.supoort.ResolverMapping;

import java.util.HashMap;
import java.util.Map;

public class ViewHandler {
    private Map<ResolverMapping, Resolver> resolvers;

    public ViewHandler() {
        resolvers = new HashMap<>();
    }

    public void addResolver(ResolverMapping resolverMapping, Resolver resolver) {
        resolvers.put(resolverMapping, resolver);
    }

    public HttpResponse handle(ModelAndView modelAndView) {
        return resolvers.get(getMatchedResolverMapping(modelAndView)).resolve(modelAndView);
    }

    private ResolverMapping getMatchedResolverMapping(ModelAndView modelAndView) {
        return resolvers.keySet().stream()
                .filter(resolver -> resolver.match(modelAndView.getViewLocation()))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }
}
