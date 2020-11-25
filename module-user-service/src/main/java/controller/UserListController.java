package controller;

import db.DataBase;
import java.io.IOException;
import java.util.Collection;
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
import utils.HandlebarUtils;

public class UserListController extends AbstractController {

    private static final String LIST_REQUEST = "/user/list";
    private static final String LOGIN_REDIRECT_LOCATION = "/user/login.html";
    private static final String LOGIN_FAIL_COOKIE_VALUE = "logined=false; Path=/";

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
        if (httpRequest.isSameUri(LIST_REQUEST)) {
            return showList(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    private HttpResponse showList(HttpRequest httpRequest) {
        Cookies cookies = httpRequest.getCookies();
        if (!cookies.isLogined()) {
            return HttpResponse.
                redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION, LOGIN_FAIL_COOKIE_VALUE);
        }

        try {
            Collection<User> users = DataBase.findAll();
            String listPage = HandlebarUtils.applyTemplate(LIST_REQUEST, users);
            byte[] body = listPage.getBytes();

            StatusLine statusLine = StatusLine.of(httpRequest, Status.OK);
            Headers headers = new Headers();
            headers.addHeader(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());
            headers.addHeader(Header.CONTENT_LENGTH, String.valueOf(body.length));

            return HttpResponse.of(statusLine, headers, body);
        } catch (IOException e) {
            return HttpResponse.of(Status.INTERNAL_ERROR);
        }
    }
}
