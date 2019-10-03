package mvc.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import server.http.response.HttpResponse;

public class ModelAndView implements View {
    private final String templatePath;
    private final Object model;

    public ModelAndView(String relativeTemplatePath, Object object) {
        this.templatePath = relativeTemplatePath;
        this.model = object;
    }

    @Override
    public HttpResponse createResponse() {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile(templatePath);
            String body = template.apply(model);
            HttpResponse response = new HttpResponse();
            response.forward(body.getBytes());
            return response;
        } catch (Exception e) {
            throw new TemplateEngineParsingException();
        }
    }
}
