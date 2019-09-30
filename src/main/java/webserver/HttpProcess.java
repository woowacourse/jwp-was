package webserver;

import http.controller.Controller;
import http.controller.ControllerHandler;
import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpProcess {
    private static final Logger logger = LoggerFactory.getLogger(HttpProcess.class);

    public HttpResponse create(BufferedReader br) throws IOException {
        HttpRequest httpRequest = RequestHandler.getInstance().create(br);
        HttpResponse httpResponse = ResponseHandler.getInstance().create(httpRequest);

        logger.debug("request path : {}", httpRequest.getPath());

        Controller controller = ControllerHandler.findByPath(httpRequest.getPath());
        controller.service(httpRequest, httpResponse);

        return httpResponse;
    }
}
