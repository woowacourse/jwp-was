package config;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateFactory {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private TemplateFactory() {
    }

    public static Template of(String location) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);

        Handlebars handlebars = new Handlebars(loader);

        return handlebars.compile(location);
    }
}
