package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.Cookies;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserListController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        final Cookies cookies = httpRequest.getCookie();
        final String logined = cookies.getValue("logined");

        if (Objects.isNull(logined) || "false".equals(logined)) {
            httpResponse.response302("/user/login.html");
        }

        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        final Handlebars handlebars = new Handlebars(loader);

        final Template template = handlebars.compile(httpRequest.getUrl());

        final Collection<User> users = DataBase.findAll();
        final byte[] userListPage = template.apply(users).getBytes();
        httpResponse.response200(userListPage);
    }
}
