package webserver.requestmapping;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class DynamicHtmlGenerator {
    public static String applyHandlebar(String path, Object model) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(path);
            return template.apply(model);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
