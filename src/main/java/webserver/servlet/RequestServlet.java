package webserver.servlet;

import exceptions.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class RequestServlet implements HttpServlet {
    private static final String METHOD_NOT_ALLOW_MESSAGE = "지원하지 않는 메소드 입니다.";

    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            return doGet(httpRequest);
        }

        if (httpRequest.getMethod() == RequestMethod.POST) {
            return doPost(httpRequest);
        }
        throw new MethodNotAllowedException(METHOD_NOT_ALLOW_MESSAGE, HttpStatus.METHOD_NOT_ALLOW);
    }

    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        throw new MethodNotAllowedException(METHOD_NOT_ALLOW_MESSAGE, HttpStatus.METHOD_NOT_ALLOW);
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        throw new MethodNotAllowedException(METHOD_NOT_ALLOW_MESSAGE, HttpStatus.METHOD_NOT_ALLOW);
    }

}
