package webserver.requestmapping.behavior;

import http.request.RequestEntity;
import http.response.ResponseEntity;

public interface RequestBehavior {
    ResponseEntity behave(RequestEntity requestEntity);
}
