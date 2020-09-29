package controller;

import com.github.jknack.handlebars.Template;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import model.dto.UsersDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TemplateMaker;

import java.io.IOException;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String PATH = "/user/list";

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Template template = TemplateMaker.handlebarsCompile(httpRequest.getPath());

            UsersDto usersDto = new UsersDto(DataBase.findAll());
            String body = template.apply(usersDto);
            httpResponse.forwardDynamicPage(httpRequest, body.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected String getPath() {
        return PATH;
    }
}
