package http.controller;

import http.HttpRequest;
import http.HttpResponse;
import service.UserService;
import utils.HttpResponseHeaderParser;

public class UserCreateController implements Controller {
    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        String header = HttpResponseHeaderParser.response405Header();
        return new HttpResponse(header);
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = new UserService();
        userService.createUser(httpRequest);
        String header = HttpResponseHeaderParser.response302Header("/index.html");
        return new HttpResponse(header);
    }
}
