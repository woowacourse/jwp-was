package dev.luffy.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dev.luffy.http.RequestMapper;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;

public class ResponseHandler {

    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);

    public static void run(HttpRequest request, HttpResponse response) {
        if (request.isStaticRequest()) {
            response.ok(request);
            return;
        }

        Method controllerMethod = RequestMapper.get(request.getPath());

        if (controllerMethod == null) {
            response.notFound(request);
            return;
        }

        try {
            controllerMethod.invoke(null, request, response);

        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("error : {}", e.getMessage());
        }
    }
}
