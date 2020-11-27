package webserver.servlet;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;
import webserver.model.Model;

public class HandlebarView extends AbstractView {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarView.class);

    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private final RequestURI uri;
    private final Handlebars handlebars;
    private final Model model;

    private HandlebarView(RequestURI uri, Handlebars handlebars,
            Model model) {
        this.uri = uri;
        this.handlebars = handlebars;
        this.model = model;
    }

    public static HandlebarView of(RequestURI uri, Model model) {
        Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader(PREFIX, SUFFIX));
        return new HandlebarView(uri, handlebars, model);
    }

    @Override
    void render(HttpRequest request, HttpResponse response) {
        try {
            Template template = this.handlebars.compile(uri.getUri().replace(".html", ""));
            String view = template.apply(model.getAttributes());
            response.ok(view.getBytes(), uri.getContentType());
        } catch (IllegalArgumentException | IOException e) {
            logger.info(e.getMessage());
            response.badRequest();
        }
    }
}
