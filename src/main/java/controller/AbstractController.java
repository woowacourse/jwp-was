package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.Method;
import request.RequestDataFormatException;
import response.HttpResponse;
import response.StatusCode;
import session.Session;

abstract public class AbstractController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public HttpResponse service(HttpRequest httpRequest, Session session) {
        try {
            return serviceWithoutExceptionCatch(httpRequest, session);
        } catch (RequestDataFormatException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.BAD_REQUEST);
        } catch (WrongRequestException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.NOT_FOUND);
        }
    }

    private HttpResponse serviceWithoutExceptionCatch(HttpRequest httpRequest, Session session) {
        if (httpRequest.isMethod(Method.GET)) {
            return doGet(httpRequest, session);
        }
        if (httpRequest.isMethod(Method.POST)) {
            return doPost(httpRequest, session);
        }
        if (httpRequest.isMethod(Method.PUT)) {
            return doPut(httpRequest, session);
        }
        if (httpRequest.isMethod(Method.DELETE)) {
            return doDelete(httpRequest, session);
        }
        throw new WrongRequestException("http method of the request is weird.");
    }

    protected HttpResponse doGet(HttpRequest httpRequest, Session session) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    protected HttpResponse doPost(HttpRequest httpRequest, Session session) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    protected HttpResponse doPut(HttpRequest httpRequest, Session session) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    protected HttpResponse doDelete(HttpRequest httpRequest, Session session) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }
}
