package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.InvalidTemplateException;

import java.io.IOException;

public class TemplateCreator {
    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    private TemplateCreator() {
    }

    public static String createTemplate(String templateLocation, Object templateContent) {
        try {
            Template template = handlebars.compile(templateLocation);
            return template.apply(templateContent);
        } catch (IOException e) {
            throw new InvalidTemplateException();
        }
    }
}
