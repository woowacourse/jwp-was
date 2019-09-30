package webserver.servlet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;
import webserver.session.HttpSessionHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends RequestServlet {
    private static final String COOKIE_USER_SESSION = "user_session";
    private static final String VIEW_USER_LIST = "user/list";
    private static final String VIEW_LOGIN = "/user/login.html";
    private static final String TEMPLATE_VALUE_MODEL = "users";
    private static final String TEMPLATE_VALUE_INDEX = "inc";
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        if (HttpSessionHelper.isValid(httpRequest.getCookie(COOKIE_USER_SESSION))) {
            return viewList();
        }
        return redirectHome();
    }

    private HttpResponse viewList() throws IOException {
        ResponseHeader header = new ResponseHeader();
        byte[] body = generateBody();
        header.setContentLengthAndType(body.length, "text/html;charset=utf-8");
        return HttpResponse.ok(header, body);
    }

    public byte[] generateBody() throws IOException {
        Map<String, Object> users = new HashMap<>();
        users.put(TEMPLATE_VALUE_MODEL, DataBase.findAll());
        return applyTemplate(users).getBytes();
    }

    private String applyTemplate(Map<String, Object> value) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PREFIX);
        loader.setSuffix(TEMPLATE_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper(TEMPLATE_VALUE_INDEX, (Helper<Integer>) (context, options) -> context + 1);
        Template template = handlebars.compile(VIEW_USER_LIST);
        return template.apply(value);
    }

    private HttpResponse redirectHome() {
        ResponseHeader header = new ResponseHeader();
        header.removeCookie(COOKIE_USER_SESSION);
        header.setLocation(VIEW_LOGIN);
        return HttpResponse.found(header);
    }
}
