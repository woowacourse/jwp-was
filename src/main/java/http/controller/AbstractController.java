package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController implements Controller {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {
            if (request.isGetMethod()) {
                doGet(request, response);
                return;
            }
            if (request.isPostMethod()) {
                doPost(request, response);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);

    public abstract void doPost(HttpRequest request, HttpResponse response);
}
