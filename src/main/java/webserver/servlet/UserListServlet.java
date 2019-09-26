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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends RequestServlet {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        ResponseHeader header = new ResponseHeader();
        if ("true".equals(httpRequest.getCookie("logined"))) {
            byte[] body = generateBody();
            header.setContentLegthAndType(body.length, "text/html;charset=utf-8");
            return HttpResponse.ok(header, body);
        }

        header.setLocation("/user/login.html");
        return HttpResponse.found(header);
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
}
