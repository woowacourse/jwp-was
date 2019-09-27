package controller.methods;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import http.session.Session;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUserListMethod implements ControllerMethod {
    @Override
    public boolean isMapping(Request request) {
        return (RequestMethod.GET == request.getRequestMethod()
                && "/user/list".equals(request.getUrl().getOriginalUrlPath()));
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException {
        Session session = request.getSession();
        if (session.getAttriubte("user") != null) {

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");
            Map<String, List<User>> users = new HashMap<>();
            List<User> userList = new ArrayList<>(DataBase.findAll());
            users.put("users", userList);
            String listPage = template.apply(users);

            response.ok()
                    .putResponseHeaders("Content-Type: ", "text/html")
                    .body(listPage.getBytes());
        }

        if (session.getAttriubte("user") == null) {
            response.found()
                    .putResponseHeaders("Location: ", "http://localhost:8080/user/login.html");
        }
    }
}
