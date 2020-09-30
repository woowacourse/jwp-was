package webserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpRequest;
import webserver.http.request.MappedRequest;
import webserver.http.response.HttpResponse;

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
                controllerMethod.invoke(null, request, response);

                response.send();
            }
        } catch (IOException | InvocationTargetException | IllegalAccessException | URISyntaxException e) {
            logger.error(e.getMessage());
            response.responseInternalServerError();
        }
    }
}
