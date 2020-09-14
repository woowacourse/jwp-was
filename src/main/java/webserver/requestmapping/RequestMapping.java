package webserver.requestmapping;

import static webserver.requestmapping.RequestMappingStorage.*;

import java.io.InputStream;

import http.request.HttpMethod;
import http.request.RequestEntity;
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

    public InputStream generateResponse(RequestEntity requestEntity) {
        return behavior.behave(requestEntity);
    }
}


