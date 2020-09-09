package web.filter;

import web.HttpRequest;
import web.HttpResponse;
import web.servlet.DispatcherServlet;
import web.servlet.Servlet;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private static final List<Filter> filters = new ArrayList<>();
    private static final Servlet servlet = new DispatcherServlet();

    static {
        filters.add(new ResourceFilter());
    }

    private int filterIndex = 0;

    public void doFilter(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (filterIndex < filters.size()) {
            Filter filter = filters.get(filterIndex);
            filterIndex++;
            filter.doFilter(httpRequest, httpResponse, this);
            return;
        }
        servlet.doService(httpRequest, httpResponse);
    }
}
