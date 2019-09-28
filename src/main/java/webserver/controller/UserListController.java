package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.ContentType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        List<User> users = new ArrayList<>(DataBase.findAll());

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/list");
            byte[] b = template.apply(users).getBytes("UTF-8");

            // contentType
            Tika tika = new Tika();
            String mimeType = tika.detect(new ByteArrayInputStream(b));
            ContentType contentType = ContentType.fromMimeType(mimeType).get();

            response.response200Header(b.length, contentType);
            response.responseBody(b);
        } catch (IOException e) {
            log.error("error: {}", e);
        }
    }
}
