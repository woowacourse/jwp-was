package web.controller.impl;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import web.controller.AbstractController;
import web.db.DataBase;
import web.model.User;
import webserver.message.exception.NotFoundFileException;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final String TEMPLATES_PATH = "/templates";
    private static final String USER_LIST_PAGE = "/user/list.html";

    @Override
    protected Response doGet(final Request request) {
        if (!request.getCookie().getCookieValue("logined").equals("true")) {
            return new Response.Builder().redirectUrl("/user/login").build();
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        Map<String, List<User>> map = new HashMap<>();
        map.put("users", users);

        // TODO 분리
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PATH);
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (Helper<Integer>) (value, options) -> value + 1);

        try {
            Template template = handlebars.compile("user/list");
            String profilePage = template.apply(map);

            return new Response.Builder().body(profilePage).build();
        } catch (IOException e) {
            throw new NotFoundFileException();
        }
    }
}
