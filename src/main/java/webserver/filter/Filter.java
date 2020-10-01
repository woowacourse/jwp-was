package webserver.filter;

import http.request.RequestEntity;
import http.response.ResponseEntity;

public interface Filter {

    boolean doFilter(RequestEntity requestEntity, ResponseEntity responseEntity);
}
