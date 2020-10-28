package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;
import utils.HttpResponseHeaderParser;

public class UserCreateController extends Controller {

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = UserService.getInstance();
        userService.createUser(httpRequest);
        String header = HttpResponseHeaderParser.found("/index.html");
        return new HttpResponse(header);
    }
}
