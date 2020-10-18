package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;

public class HandlebarUtils {

    private static final String TEMPLATES_DIR = "/templates";
    private static final String HTML_EXTENSION = ".html";

    public static String apply(String location, Object object) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_DIR);
        loader.setSuffix(HTML_EXTENSION);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(location);
        return template.apply(object);
    }
}
