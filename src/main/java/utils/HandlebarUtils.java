package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;

public class HandlebarUtils {

    private static final String TEMPLATE_LOCATION = "/templates";
    private static final String HTML_EXTENSION = ".html";

    public static Template loadHandlebar(String location) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_LOCATION);
        loader.setSuffix(HTML_EXTENSION);
        Handlebars handlebars = new Handlebars(loader);

        return handlebars.compile(location);
    }
}
