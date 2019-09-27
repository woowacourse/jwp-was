package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.Database;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserListController extends AbstractController {
    private static final String TEXT_HTML = "text/html";

    @Override
    protected HttpResponse getMapping(HttpRequest request) {
        try {
            Map<String, Object> users = new HashMap<>();
            users.put("users", Database.findAll());

            return templateEngine("user/list", users)
                    .map(body -> HttpResponse.success(request, TEXT_HTML, body))
                    .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return HttpResponse.INTERNAL_SERVER_ERROR;
        }
    }

    public Optional<String> templateEngine(String filePath, Object object) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader(filePath);
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(filePath);

        return Optional.of(template.apply(object));
    }
}
