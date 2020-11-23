package controller;

import exception.NoSessionException;
import java.io.IOException;
import model.domain.User;
import model.general.ContentType;
import model.general.Cookies;
import model.general.Header;
import model.general.Headers;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import model.session.HttpSession;
import utils.HandlebarUtils;

public class UserProfileController extends UserController {

    private static final String PROFILE_REQUEST = "/user/profile";
    private static final String LOGIN_REDIRECT_LOCATION = "/user/login.html";
    private static final String USER_ATTRIBUTE_KEY = "user";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Method method = httpRequest.getMethod();

        if (method.equals(Method.GET)) {
            return doGet(httpRequest);
        }

        return super.service(httpRequest);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        if (httpRequest.isSameUri(PROFILE_REQUEST)) {
            return showProfile(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    private HttpResponse showProfile(HttpRequest httpRequest) {
        Cookies cookies = httpRequest.getCookies();
        if (!cookies.isLogined()) {
            return redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION);
        }

        try {
            HttpSession httpSession = httpRequest.getSession();
            User user = (User) httpSession.getAttribute(USER_ATTRIBUTE_KEY);
            String postPage = HandlebarUtils.applyTemplate(PROFILE_REQUEST, user);
            byte[] body = postPage.getBytes();

            StatusLine statusLine = StatusLine.of(httpRequest, Status.OK);
            Headers headers = new Headers();
            headers.addHeader(Header.CONTENT_LENGTH, String.valueOf(body.length));
            headers.addHeader(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());

            return HttpResponse.of(statusLine, headers, body);
        } catch (IOException e) {
            return HttpResponse.of(Status.INTERNAL_ERROR);
        } catch (NoSessionException e) {
            return redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION);
        }
    }
}
