package config;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateFactory {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";
    private static final TemplateLoader LOADER = new ClassPathTemplateLoader(PREFIX, SUFFIX);
    private static final Handlebars HANDLEBARS = new Handlebars(LOADER);

    private TemplateFactory() {
    }

    public static Template of(String location) throws IOException {
        return HANDLEBARS.compile(location);
    }
}
