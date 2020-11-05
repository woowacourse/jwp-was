package client.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.Users;
import web.HttpSession;
import web.controller.Controller;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.response.HttpStatusCode;

import java.io.IOException;
import java.util.Optional;

public class UserListController implements Controller {

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getSession();
        boolean logined = (boolean) Optional.ofNullable(httpSession.getAttribute("logined"))
                .orElse(false);

        if (logined) {
            String profilePage = parseTemplate("user/profile", new Users(DataBase.findAll()));
            httpResponse.response(HttpStatusCode.OK, profilePage.getBytes());
            return;
        }
        httpResponse.sendRedirect("/user/login.html");
    }

    private String parseTemplate(String path, Object data) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(path);
            return template.apply(data);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s : 페이지가 존재하지 않습니다.", path));
        }
    }
}
