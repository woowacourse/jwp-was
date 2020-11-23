package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateUtils {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";
    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);

        handlebars = new Handlebars(loader);
        handlebars.registerHelper("increase", (Helper<Integer>) (number, options) -> number + 1);
    }

    public static Template buildTemplate(String path) throws IOException {
        return handlebars.compile(path);
    }
}
