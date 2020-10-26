package config;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class TemplateFactory {
    private static final TemplateLoader loader = new ClassPathTemplateLoader();
    private static final Handlebars handlebars = new Handlebars();
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private TemplateFactory() {
    }

    public static Template of(String location) throws IOException {
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);

        handlebars.with(loader);

        return handlebars.compile(location);
    }
}
