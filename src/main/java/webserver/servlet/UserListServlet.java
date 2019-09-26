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
import webserver.session.HttpSession;
import webserver.session.HttpSessionHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends RequestServlet {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        if (isSessionValid(httpRequest.getCookie("user_session"))) {
            return viewList();
        }
        return redirectHome();
    }

    private boolean isSessionValid(String requestUserSessionId) {
        HttpSession session = HttpSessionHelper.get(requestUserSessionId);
        return requestUserSessionId != null && session != null;
    }

    private HttpResponse viewList() throws IOException {
        ResponseHeader header = new ResponseHeader();
        byte[] body = generateBody();
        header.setContentLegthAndType(body.length, "text/html;charset=utf-8");
        return HttpResponse.ok(header, body);
    }

    public byte[] generateBody() throws IOException {
        Map<String, Object> users = new HashMap<>();
        users.put("users", DataBase.findAll());
        return applyTemplate(users).getBytes();
    }

    private String applyTemplate(Map<String, Object> value) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);
        Template template = handlebars.compile("user/list");
        return template.apply(value);
    }

    private HttpResponse redirectHome() {
        ResponseHeader header = new ResponseHeader();
        header.removeCookie("user_session");
        header.setLocation("/user/login.html");
        return HttpResponse.found(header);
    }
}
