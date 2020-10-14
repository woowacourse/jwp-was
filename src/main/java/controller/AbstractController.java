package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.Method;
import request.RequestDataFormatException;
import response.HttpResponse;
import response.StatusCode;

abstract public class AbstractController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        try {
            return serviceWithoutExceptionCatch(httpRequest);
        } catch (RequestDataFormatException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.BAD_REQUEST);
        } catch (WrongRequestException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.NOT_FOUND);
        }
    }

    private HttpResponse serviceWithoutExceptionCatch(HttpRequest httpRequest) {
        if (httpRequest.isMethod(Method.GET)) {
            return doGet(httpRequest);
        }
        if (httpRequest.isMethod(Method.POST)) {
            return doPost(httpRequest);
        }
        if (httpRequest.isMethod(Method.PUT)) {
            return doPut(httpRequest);
        }
        if (httpRequest.isMethod(Method.DELETE)) {
            return doDelete(httpRequest);
        }
        throw new WrongRequestException("http method of the request is weird.");
    }

    protected HttpResponse doGet(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    protected HttpResponse doPost(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    protected HttpResponse doPut(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    protected HttpResponse doDelete(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }
}
