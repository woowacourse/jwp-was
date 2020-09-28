package http.controller;

import http.HttpRequest;
import http.HttpResponse;
import service.UserService;
import utils.HttpResponseHeaderParser;

public class UserCreateController implements Controller {
    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        String header = HttpResponseHeaderParser.methodNotAllowed();
        return new HttpResponse(header);
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = new UserService();
        userService.createUser(httpRequest);
        String header = HttpResponseHeaderParser.found("/index.html");
        return new HttpResponse(header);
    }
}
