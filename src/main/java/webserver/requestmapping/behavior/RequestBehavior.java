package webserver.requestmapping.behavior;

import http.request.RequestEntity;
import http.response.ResponseEntity;

public interface RequestBehavior {
    void behave(RequestEntity requestEntity, ResponseEntity responseEntity);
}
