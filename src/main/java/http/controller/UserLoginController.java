package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;
import utils.HttpResponseHeaderParser;

public class UserLoginController extends Controller {

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = UserService.getInstance();
        boolean auth = userService.authenticateUser(httpRequest);
        String header;
        if (auth) {
            header = HttpResponseHeaderParser.found("/", true);
        } else {
            header = HttpResponseHeaderParser.found("/user/login_failed.html", false);
        }
        return new HttpResponse(header);
    }
}
