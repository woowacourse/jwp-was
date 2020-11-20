package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import http.Cookies;
import http.HttpSessionStorage;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import type.method.MethodType;
import utils.TemplateUtils;

public class UserListController extends AbstractController {

    private static final String REDIRECT_LOGIN = "/user/login.html";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        final Cookies cookies = httpRequest.getCookie();
        final String SessionId = cookies.getSessionId();

        if (Objects.isNull(SessionId) || !HttpSessionStorage.isExistById(SessionId)) {
            httpResponse.response302(REDIRECT_LOGIN);
        }

        final Template template = TemplateUtils.createHTML(httpRequest.getUrl());

        final Collection<User> users = DataBase.findAll();
        final byte[] userListPage = template.apply(users).getBytes();
        httpResponse.response200(userListPage);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.response405(MethodType.GET.name());
    }
}
