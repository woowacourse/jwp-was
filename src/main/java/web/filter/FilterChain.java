package web.filter;

import web.HttpRequest;
import web.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private static final List<Filter> filters = new ArrayList<>();

    static {
        filters.add(new ResourceFilter());
    }

    private int filterIndex = 0;

    public void doFilter(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (filterIndex < filters.size()) {
            Filter filter = filters.get(filterIndex);
            filterIndex++;
            filter.doFilter(httpRequest, httpResponse, this);
        }
    }
}
