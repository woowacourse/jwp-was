package webserver.requestmapping;

import http.request.HttpMethod;
import http.request.RequestEntity;
import http.response.ResponseEntity;
import webserver.requestmapping.behavior.RequestBehavior;

public class RequestMapping {

    private HttpMethod httpMethod;
    private String path;
    private RequestBehavior behavior;

    public RequestMapping(
        HttpMethod httpMethod, String path, RequestBehavior requestBehavior
    ) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.behavior = requestBehavior;
    }

    public boolean isMapping(HttpMethod httpMethod, String path) {
        return this.httpMethod == httpMethod && this.path.equals(path);
    }

    public void process(RequestEntity requestEntity, ResponseEntity responseEntity) {
        behavior.behave(requestEntity, responseEntity);
    }
}


