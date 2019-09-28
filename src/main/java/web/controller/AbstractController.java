package web.controller;

import webserver.message.HttpMethod;
import webserver.message.exception.NotFoundFileException;
import webserver.message.request.Request;
import webserver.message.response.Response;

public class AbstractController implements Controller {

    @Override
    public Response service(final Request request) {
        if (request.matchesMethod(HttpMethod.GET)) {
            return doGet(request);
        }
        if (request.matchesMethod(HttpMethod.POST)) {
            return doPost(request);
        }

        throw new NotFoundFileException();
    }

    protected Response doGet(final Request request) {
        throw new NotFoundFileException();
    }

    protected Response doPost(final Request request) {
        throw new NotFoundFileException();
    }
}
