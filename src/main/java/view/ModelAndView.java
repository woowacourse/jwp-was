package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.MimeType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;

public class ModelAndView implements View {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Model model;
    private String viewName;

    public ModelAndView(Model model, String viewName) {
        this.model = model;
        this.viewName = viewName;
    }

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(viewName);
            String listPage = template.apply(model.getAttributes());
            httpResponse.response2xx(listPage.getBytes(), MimeType.HTML.getMimeType());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
