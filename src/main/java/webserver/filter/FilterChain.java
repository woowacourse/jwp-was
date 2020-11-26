package webserver.filter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;

public interface FilterChain {
    void doFilter(HttpRequest request, HttpResponse response) throws IOException;
}
