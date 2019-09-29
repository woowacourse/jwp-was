package view;


import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.exception.LoadFileFailedException;

import java.io.IOException;
import java.util.Map;

public class HandlebarsManager implements TemplateManager {
    private final Handlebars handlebars;

    public HandlebarsManager(TemplateLoader templateLoader) {
        this.handlebars = new Handlebars(templateLoader);
    }

    @Override
    public byte[] applyCompile(String viewName, Map<String, Object> modelMap) {
        try {
            Template template = handlebars.compile(viewName);
            return template.apply(modelMap).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoadFileFailedException(e);
        }
    }
}
