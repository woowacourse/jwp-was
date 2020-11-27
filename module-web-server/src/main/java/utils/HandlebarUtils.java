package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;

public class HandlebarUtils {

    private static final String TEMPLATE_LOCATION = "/templates";
    private static final String HTML_EXTENSION = ".html";
    private static final TemplateLoader loader
        = new ClassPathTemplateLoader(TEMPLATE_LOCATION, HTML_EXTENSION);
    private static final Handlebars handlebars = new Handlebars(loader);

    public static String applyTemplate(String location, Object object) throws IOException {
        Template template = handlebars.compile(location);
        return template.apply(object);
    }
}
