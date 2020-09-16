package webserver.requestmapping;

import static webserver.requestmapping.RequestMappingStorage.*;

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
        if (FileExtensionType.isSupportedFile(path)) {
            return this.httpMethod == httpMethod && this.path.equals(FILE_PATH);
        }

        return this.httpMethod == httpMethod && this.path.equals(path);
    }

    public ResponseEntity generateResponse(RequestEntity requestEntity) {
        return behavior.behave(requestEntity);
    }
}


