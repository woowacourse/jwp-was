package http.view;

import http.model.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class ViewHandler {
    private List<Resolver> resolvers;

    public ViewHandler() {
        resolvers = new ArrayList<>();
    }

    public void addResolver(Resolver viewResolver) {
        resolvers.add(viewResolver);
    }

    public HttpResponse handle(ModelAndView modelAndView) {
        return resolvers.get(0).resolve(modelAndView);
    }
}
