package controller;

import exception.RequestMethodNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.request.MethodType;
import web.response.HttpResponse;

public abstract class AbstractController implements Controller {
    public static final String BAD_REQUEST_POST_ERROR_MESSAGE = "지원하지 않는 POST 요청입니다.";
    public static final String BAD_REQUEST_GET_ERROR_MESSAGE = "지원하지 않는 GET 요청입니다.";
    protected static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        MethodType method = request.getMethod();
        if (method.isGet()) {
            doGet(request, response);
        } else if (method.isPost()) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        response.badRequest(BAD_REQUEST_POST_ERROR_MESSAGE);
        throw new RequestMethodNotSupportedException("POST");
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        response.badRequest(BAD_REQUEST_GET_ERROR_MESSAGE);
        throw new RequestMethodNotSupportedException("GET");
    }
}
