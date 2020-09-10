package controller;

import http.request.Request;
import http.response.Response;

public class HomeController implements Controller{

    @Override
    public void run(Request request, Response response) {
        byte[] body = "Hello Word".getBytes();
        response.response200Header(body.length);
        response.responseBody(body);
    }
}
