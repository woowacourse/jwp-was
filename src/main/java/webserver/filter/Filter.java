package webserver.filter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

public interface Filter {
    void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) throws IOException;
}
