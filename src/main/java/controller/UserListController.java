package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.common.HttpSession;
import http.parser.HttpUriParser;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseBody;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserListController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(UserListController.class);
    private static final String USER_LIST_PATH = "/user/list.html";
    private static final String LOGIN_PATH = "/user/login.html";
    private static final String USER_LIST_URI = "/user/list";
    private static final RequestMapping REQUEST_MAPPING = RequestMapping.of(HttpMethod.GET, HttpUriParser.parse(USER_LIST_URI));

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {

        HttpSession httpSession = httpRequest.getSession();

        if (isLogined(httpRequest)) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            try {
                Template template = handlebars.compile("user/list");
                Map<String, Collection<User>> users = Collections.singletonMap("users", DataBase.findAll());
                httpResponse.forward(new ResponseBody(template.apply(users).getBytes()));
                return;
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        httpResponse.redirect(LOGIN_PATH);
    }

    private boolean isLogined(final HttpRequest httpRequest) {
        return "true".equals(httpRequest.getCookie("logined"));
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return REQUEST_MAPPING.equals(requestMapping);
    }
}
