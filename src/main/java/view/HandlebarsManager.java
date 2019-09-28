package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import view.exception.TemplateException;

import java.io.IOException;
import java.util.Map;

public class HandlebarsManager implements TemplateEngineManager {
    private final Handlebars handlebars;

    public HandlebarsManager(TemplateLoader templateLoader, String prefix, String suffix) {
        templateLoader.setPrefix(prefix);
        templateLoader.setSuffix(suffix);
        handlebars = new Handlebars(templateLoader);
    }

    @Override
    public String getCompiledTemplate(String viewName, Map<String, Object> model) throws TemplateException {
        try {
            Template template = handlebars.compile(viewName);
            return template.apply(model);
        } catch (IOException e) {
            throw new TemplateException();
        }
    }
}
