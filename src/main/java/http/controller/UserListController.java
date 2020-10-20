package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import utils.HttpResponseHeaderParser;

import java.io.IOException;

public class UserListController extends Controller {
    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/list");

            byte[] userListPage = template.apply(DataBase.findAll()).getBytes();
            String header = HttpResponseHeaderParser.ok("text/html", userListPage.length);
            return new HttpResponse(header, userListPage);
        } catch (IOException e) {
            return new HttpResponse(HttpResponseHeaderParser.internalServerError(), null);
        }
    }
}
