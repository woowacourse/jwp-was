package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import fileloader.exception.LoadFileFailedException;

import java.io.IOException;
import java.util.Map;

public class HandlebarsManager implements TemplateEngineManager {
    private final Handlebars handlebars;

    public HandlebarsManager(TemplateLoader templateLoader) {
        handlebars = new Handlebars(templateLoader);
    }

    @Override
    public byte[] applyCompile(String viewName, Map<String, Object> model) {
        try {
            Template template = handlebars.compile(viewName);
            return template.apply(model).getBytes();
        } catch (IOException e) {
            throw new LoadFileFailedException(e);
        }
    }
}
