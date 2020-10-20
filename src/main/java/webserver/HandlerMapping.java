package webserver;

import webserver.controller.Controllers;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.staticfile.StaticFileMatcher;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private Map<String, Controllers> mappings = new HashMap<>();

    public HandlerMapping() {
        mappings.put("/user/create", Controllers.USER_CONTROLLER);
    }

    public void mapping(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (StaticFileMatcher.isStaticFileResourcePath(httpRequest.getResourcePath())) {
            Controllers.STATIC_FILE_CONTROLLER.doService(httpRequest, httpResponse);
            return;
        }

        mappings.get(httpRequest.getResourcePath()).doService(httpRequest, httpResponse);
    }
}
