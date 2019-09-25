package webserver.servlet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

import java.io.IOException;
import java.util.Collection;

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

    private byte[] generateBody() throws IOException {
        Template template = generateTemplate();
        Collection<User> users = DataBase.findAll();
        return template.apply(users).getBytes();
    }

    private Template generateTemplate() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        return handlebars.compile("user/list");
    }
}
