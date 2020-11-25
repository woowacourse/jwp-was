package utils;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class TemplateUtils {

    private static final TemplateLoader loader = new ClassPathTemplateLoader();

    public static Template createHTML(final String location) throws IOException {
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        final Handlebars handlebars = new Handlebars(loader);
        return handlebars.compile(location);
    }
}
