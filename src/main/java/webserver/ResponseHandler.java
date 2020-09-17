package webserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import http.request.MappedRequest;
import http.response.HttpResponse;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static void run(HttpRequest request, HttpResponse response) {
        MappedRequest mappedRequest = new MappedRequest(request.getHttpMethod(), request.getHttpPath());

        try {
            if (request.isStaticFile()) {
                response.responseOk(request);
            } else if (!RequestMapper.isContain(mappedRequest)) {
                response.responseNotFound();
            } else {
                Method controllerMethod = RequestMapper.get(mappedRequest);
                String redirectPath = (String)controllerMethod.invoke(null, request, response);

                response.responseFound(redirectPath);
            }
        } catch (IOException | InvocationTargetException | IllegalAccessException | URISyntaxException e) {
            logger.error(e.getMessage());
        }

    }
}
