package webserver.support;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Optional;

public class HandlebarsTemplateEngine implements TemplateEngine {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private Handlebars handlebars;

    public HandlebarsTemplateEngine(String path) {
        TemplateLoader loader = new ClassPathTemplateLoader(path);
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        this.handlebars = new Handlebars(loader);
    }

    @Override
    public Optional<String> apply(String path, Object object) {
        try {
            Template template = handlebars.compile(path);
            return Optional.ofNullable(template.apply(object));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
