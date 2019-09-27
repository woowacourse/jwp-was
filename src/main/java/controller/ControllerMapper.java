package controller;

import controller.exception.NotFoundUrlException;
import model.http.HttpRequest;
import model.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static void mapAndHandle(HttpRequest request, HttpResponse response) {
        Method method = mappingResourceMethod(request);
        if (method == null) {
            method = Controllers.REQUEST_MAPPING_METHODS.get(request.getPath());
        }
        method.setAccessible(true);

        try {
            method.invoke(Controllers.REQUEST_MAPPING_CONTROLLERS.get(method), request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            throw new NotFoundUrlException(e);
        }
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
        return isStaticFileRequest(request, extension);
    }

}
