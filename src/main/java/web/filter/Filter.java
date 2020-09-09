package web.filter;

import web.HttpRequest;
import web.HttpResponse;

import java.io.IOException;

public interface Filter {

    void doFilter(HttpRequest httpRequest, HttpResponse httpResponse, FilterChain filterChain) throws IOException;
}
