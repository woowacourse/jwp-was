package web.filter;

import web.HttpRequest;
import web.HttpResponse;

public interface Filter {

    void doFilter(HttpRequest httpRequest, HttpResponse httpResponse, FilterChain filterChain);
}
