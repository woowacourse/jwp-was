package controller;

import com.github.jknack.handlebars.Template;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import model.dto.UsersDto;
import utils.TemplateMaker;

import java.io.IOException;

public class UserListController extends AbstractController {
    private static final String PATH = "/user/list";

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            if (httpRequest.isNotLogined()) {
                httpResponse.sendRedirect("/user/login.html");
                return;
            }
            Template template = TemplateMaker.handlebarsCompile(httpRequest.getPath());
            UsersDto usersDto = new UsersDto(DataBase.findAll());
            String body = template.apply(usersDto);
            httpResponse.forward(httpRequest, body.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getPath() {
        return PATH;
    }
}
