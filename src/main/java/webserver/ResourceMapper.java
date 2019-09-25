package webserver;

import controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import utils.FileIoUtils;

import java.net.URISyntaxException;

public class ResourceMapper {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    public static HttpResponseEntity map(HttpRequest httpRequest) throws URISyntaxException {
        String path = httpRequest.getUri().getPath();
        Controller controller = Controllers.getController(path);
        if (controller != null) {
            return controller.handle(httpRequest);
        }
        return findResource(STATIC_PATH + path, TEMPLATES_PATH + path);
    }

    private static HttpResponseEntity findResource(String... paths) throws URISyntaxException {
        for (String path : paths) {
            if (FileIoUtils.existFileInClasspath(path)) {
                return HttpResponseEntity.get200Response(path);
            }
        }
        return HttpResponseEntity.get404Response();
    }
}