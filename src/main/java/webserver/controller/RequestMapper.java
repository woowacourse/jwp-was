package webserver.controller;

import utils.FilePathUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeaders;

import java.util.HashMap;
import java.util.Map;

import static webserver.response.ResponseStatus.OK;

public class RequestMapper {
    private static final Map<String, Responsive> requestMapping = new HashMap<>();

    static {
        requestMapping.put("/index.html", IndexController.goIndex());
        requestMapping.put("/user/form.html", UserController.goForm());
        requestMapping.put("/user/create", UserController.createUser());
    }

    public static HttpResponse controller(HttpRequest httpRequest) {
        String path = httpRequest.getPath();
        if (requestMapping.containsKey(path)) {
            return requestMapping.get(path).apply(httpRequest);
        }
        return getStaticFileResponse(httpRequest);
    }

    private static HttpResponse getStaticFileResponse(HttpRequest httpRequest) {
        String path = httpRequest.getPath();
        HttpResponse httpResponse = new HttpResponse(OK, new ResponseHeaders(), new ResponseBody(path));
        httpResponse.buildGetHeader(FilePathUtils.getExtension(path));
        return httpResponse;
    }
}
