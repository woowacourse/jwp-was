package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.exception.NonLoginException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.SessionManager;
import webserver.http.headerfields.HttpMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractController implements Controller {
    protected final SessionManager sessionManager = SessionManager.getInstance();
    private final Map<HttpMethod, Controller> mapping = new HashMap<>();

    {
        mapping.put(HttpMethod.GET, this::getMapping);
        mapping.put(HttpMethod.POST, this::postMapping);
    }

    protected Optional<String> templateEngine(String filePath, Object object) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader(filePath);
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(filePath);

        return Optional.of(template.apply(object));
    }

    protected void checkLogin(HttpRequest request) {
        if (!request.cookie().contains("logined=true")) {
            throw new NonLoginException();
        }
    }

    @Override
    public HttpResponse service(HttpRequest request) {
        HttpMethod method = request.method();
        return mapping.get(method).service(request);
    }

    protected HttpResponse getMapping(HttpRequest request) {
        return null;
    }

    protected HttpResponse postMapping(HttpRequest request) {
        return null;
    }
}