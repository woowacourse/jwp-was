package util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class TemplateMaker {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    public static Template handlebarsCompile(String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("plusOne", (Helper<Integer>) (context, options) -> context + 1);
        return handlebars.compile(path);
    }
}
