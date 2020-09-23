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
            throw new WrongUriException("http method of the request is weird.");
        } catch (RequestDataFormatException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.BAD_REQUEST);
        } catch (WrongUriException e) {
            logger.debug(e.getMessage());
            return new HttpResponse(StatusCode.NOT_FOUND);
        }
    }

    abstract protected HttpResponse doGet(HttpRequest httpRequest);

    abstract protected HttpResponse doPost(HttpRequest httpRequest);

    abstract protected HttpResponse doPut(HttpRequest httpRequest);

    abstract protected HttpResponse doDelete(HttpRequest httpRequest);
}
