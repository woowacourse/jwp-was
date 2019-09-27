package http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandlebarView {

    private static final String TEMPLATES = "/templates";
    private static final String HTML = ".html";

    public String render(String viewName, Map<String, Object> model) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix(TEMPLATES);
            loader.setSuffix(HTML);
            Handlebars handlebars = new Handlebars(loader);
            Map<String, Object> models = new HashMap<>(model);
            Template template = handlebars.compile(viewName);
            return template.apply(models);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
