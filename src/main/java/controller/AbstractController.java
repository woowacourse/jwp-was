package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoggingUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.response.HttpStatus.INTERNAL_SERVER_ERROR;
import static http.response.HttpStatus.NOT_FOUND;

public abstract class AbstractController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.match(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
        }
        if (HttpMethod.POST.match(httpRequest.getMethod())) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.setStatus(NOT_FOUND);
            httpResponse.forward("/error.html");
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatus(INTERNAL_SERVER_ERROR);
            LoggingUtils.logStackTrace(logger, e);
        }
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatus(NOT_FOUND);
    }
}
