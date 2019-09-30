package webserver.servlet;

import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.session.HttpSessionHelper;
import webserver.view.RedirectView;
import webserver.view.UserListView;
import webserver.view.View;

public class UserListServlet extends RequestServlet {
    private static final String COOKIE_USER_SESSION = "user_session";
    private static final String VIEW_USER_LIST = "user/list";
    private static final String VIEW_LOGIN = "/user/login.html";
    private static final String TEMPLATE_VALUE_MODEL = "users";

    @Override
    public View doGet(HttpRequest request, HttpResponse response) {
        if (HttpSessionHelper.isValid(request.getCookie(COOKIE_USER_SESSION))) {
            return viewList(request);
        }
        return redirectHome(response);
    }

    private View viewList(HttpRequest request) {
        request.setAttribute(TEMPLATE_VALUE_MODEL, DataBase.findAll());
        return new UserListView(VIEW_USER_LIST);
    }

    private View redirectHome(HttpResponse response) {
        response.removeCookie(COOKIE_USER_SESSION);
        return new RedirectView(VIEW_LOGIN);
    }
}
