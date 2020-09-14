package webserver;

import static webserver.RequestMappingStorage.*;

import java.io.InputStream;
import java.util.function.Function;

import http.request.HttpMethod;
import http.request.RequestEntity;

public class RequestMapping {

    private HttpMethod httpMethod;
    private String path;
    private Function<RequestEntity, InputStream> behavior;

    public RequestMapping(
        HttpMethod httpMethod, String path, Function<RequestEntity, InputStream> behavior
    ) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.behavior = behavior;
    }

    public boolean isMapping(HttpMethod httpMethod, String path) {
        if (FileExtensionType.isSupportedFile(path)) {
            return this.httpMethod == httpMethod && this.path.equals(FILE_PATH);
        }

        return this.httpMethod == httpMethod && this.path.equals(path);
    }

    public InputStream generateResponse(RequestEntity requestEntity) {
        return behavior.apply(requestEntity);
    }
}


