package controller;

import java.util.HashMap;
import java.util.Map;
import model.general.ContentType;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import service.UserService;

public class UserController extends AbstractController {

    private static final String USER_CREATE_REQUEST = "/user/create";
    private static final String LOGIN_REQUEST = "/user/login";
    private static final String HOME_LOCATION = "/index.html";
    private static final String LOGIN_FAIL_REDIRECT_LOCATION = "/user/login_failed.html";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Method method = httpRequest.getMethod();

        if (method.equals(Method.POST)) {
            return doPost(httpRequest);
        }

        return super.service(httpRequest);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        //todo: enum으로 처리
        if (httpRequest.isSameUri(USER_CREATE_REQUEST)) {
            return createUser(httpRequest);
        }
        if (httpRequest.isSameUri(LOGIN_REQUEST)) {
            return login(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    public static HttpResponse createUser(HttpRequest httpRequest) {
        Map<Header, String> headers = new HashMap<>();
        UserService service = UserService.getInstance();

        service.createUser(httpRequest);

        StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
        headers.put(Header.LOCATION, HOME_LOCATION);

        return HttpResponse.of(statusLine, headers, null);
    }

    private HttpResponse login(HttpRequest httpRequest) {
        Map<Header, String> headers = new HashMap<>();
        UserService service = UserService.getInstance();
        StatusLine statusLine;

        try {
            service.login(httpRequest);
            statusLine = StatusLine.of(httpRequest, Status.FOUND);
            headers.put(Header.LOCATION, HOME_LOCATION);
            headers.put(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());
            headers.put(Header.SET_COOKIE, "logined=true; Path=/");
        } catch (IllegalArgumentException e) {
            statusLine = StatusLine.of(httpRequest, Status.FOUND);
            headers.put(Header.LOCATION, LOGIN_FAIL_REDIRECT_LOCATION);
            headers.put(Header.SET_COOKIE, "logined=false; Path=/");
        }

        return HttpResponse.of(statusLine, headers, null);
    }
}
