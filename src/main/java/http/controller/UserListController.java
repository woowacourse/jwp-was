package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.HttpCookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpCookie requestHttpCookie = httpRequest.getHttpCookie();
        if ("true".equals(requestHttpCookie.getCookie("logined"))) {
            try {
                Map<String, Object> data = new HashMap<>();
                Collection<User> users = DataBase.findAll();
                data.put("users", users);

                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                loader.setCharset(StandardCharsets.UTF_8);

                Handlebars handlebars = new Handlebars(loader);
                Template template = handlebars.compile("/user/list");
                httpResponse.setResponseBody(template.apply(data).getBytes(), "/user/list.html");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        httpResponse.redirect("/index.html");
    }
}
