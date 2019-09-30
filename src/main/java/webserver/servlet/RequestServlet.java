package webserver.servlet;

import exceptions.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.view.View;

public abstract class RequestServlet implements HttpServlet {
    private static final String METHOD_NOT_ALLOW_MESSAGE = "지원하지 않는 메소드 입니다.";

    @Override
    public View run(HttpRequest request, HttpResponse response) {
        if (request.getMethod() == RequestMethod.GET) {
            return doGet(request, response);
        }

        if (request.getMethod() == RequestMethod.POST) {
            return doPost(request, response);
        }
        throw new MethodNotAllowedException(METHOD_NOT_ALLOW_MESSAGE, HttpStatus.METHOD_NOT_ALLOW);
    }

    public View doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(METHOD_NOT_ALLOW_MESSAGE, HttpStatus.METHOD_NOT_ALLOW);
    }

    public View doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(METHOD_NOT_ALLOW_MESSAGE, HttpStatus.METHOD_NOT_ALLOW);
    }
}
