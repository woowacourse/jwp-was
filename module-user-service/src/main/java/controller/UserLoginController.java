package controller;

import exception.NoSessionException;
import exception.NotExistUserException;
import model.domain.User;
import model.general.ContentType;
import model.general.Header;
import model.general.Headers;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import model.session.HttpSession;
import service.UserService;

public class UserLoginController extends AbstractController {

    private static final String LOGIN_REQUEST = "/user/login";
    private static final String HOME_LOCATION = "/index.html";
    private static final String LOGIN_FAIL_REDIRECT_LOCATION = "/user/login_failed.html";
    private static final String LOGIN_REDIRECT_LOCATION = "/user/login.html";
    private static final String USER_ATTRIBUTE_KEY = "user";
    private static final String LOGIN_SUCCESS_COOKIE_VALUE = "logined=true; Path=/";
    private static final String LOGIN_FAIL_COOKIE_VALUE = "logined=false; Path=/";

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
        if (httpRequest.isSameUri(LOGIN_REQUEST)) {
            return login(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    private HttpResponse login(HttpRequest httpRequest) {
        UserService service = UserService.getInstance();

        try {
            User user = service.login(httpRequest);
            HttpSession httpSession = httpRequest.getSession();
            httpSession.setAttribute(USER_ATTRIBUTE_KEY, user);

            StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
            Headers headers = new Headers();
            headers.addHeader(Header.LOCATION, HOME_LOCATION);
            headers.addHeader(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());
            headers.addHeader(Header.SET_COOKIE, LOGIN_SUCCESS_COOKIE_VALUE);

            return HttpResponse.of(statusLine, headers, null);
        } catch (NotExistUserException e) {
            return HttpResponse.
                redirectResponse(httpRequest, LOGIN_FAIL_REDIRECT_LOCATION,
                    LOGIN_FAIL_COOKIE_VALUE);
        } catch (NoSessionException e) {
            return HttpResponse.
                redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION, LOGIN_FAIL_COOKIE_VALUE);
        }
    }
}
