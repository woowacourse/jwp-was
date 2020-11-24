package controller;

import model.general.Header;
import model.general.Headers;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;

public class UserController extends AbstractController {

    private static final String LOGIN_FAIL_COOKIE_VALUE = "logined=false; Path=/";

    protected HttpResponse redirectResponse(HttpRequest httpRequest, String location) {
        StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
        Headers headers = new Headers();
        headers.addHeader(Header.LOCATION, location);
        headers.addHeader(Header.SET_COOKIE, LOGIN_FAIL_COOKIE_VALUE);

        return HttpResponse.of(statusLine, headers, null);
    }
}
