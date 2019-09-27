package controller.methods;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.support.TemplateManager;
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

            String page = TemplateManager.getTemplateProcessedPage();
            response.ok()
                    .putResponseHeaders("Content-Type: ", "text/html")
                    .body(page.getBytes());
        }

        if (session.getAttriubte("user") == null) {
            response.found()
                    .putResponseHeaders("Location: ", "http://localhost:8080/user/login.html");
        }
    }
}
