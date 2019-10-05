package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.lang.reflect.Method;

public class ControllerMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static Method mappingMethod(HttpRequest request, HttpResponse response) {
        Method method = mappingResourceMethod(request);
        if (method == null) {
            method = Controllers.REQUEST_MAPPING_METHODS.get(request.getPath());
        }
        method.setAccessible(true);
        return method;
    }

    private static Method mappingResourceMethod(HttpRequest request) {
        if (isTemplateFileRequest(request, ".html")) {
            return Controllers.REQUEST_MAPPING_METHODS.get("templates");
        }

        if (isStaticFileRequest(request, ".")) {
            return Controllers.REQUEST_MAPPING_METHODS.get("static");
        }
        return null;
    }

    private static boolean isStaticFileRequest(HttpRequest request, String delimiter) {
        return request.getPath().contains(delimiter);
    }

    private static boolean isTemplateFileRequest(HttpRequest request, String extension) {
        return request.getPath().contains(extension) || request.getPath().equals("/");
    }

}
