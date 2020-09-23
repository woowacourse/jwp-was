package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.Method;
import request.WrongRequestFormatException;
import response.HttpResponse;
import response.StatusCode;

abstract public class AbstractController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        try {
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
            return new HttpResponse(StatusCode.NOT_FOUND);
        } catch (WrongRequestFormatException | WrongUriException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.BAD_REQUEST);
        }
    }

    abstract protected HttpResponse doGet(HttpRequest httpRequest);

    abstract protected HttpResponse doPost(HttpRequest httpRequest);

    abstract protected HttpResponse doPut(HttpRequest httpRequest);

    abstract protected HttpResponse doDelete(HttpRequest httpRequest);
}
