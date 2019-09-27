package handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class TemplateEngine {
    private static final String LOADER_PREFIX = "/templates";
    private static final String LOADER_SUFFIX = ".html";

    private static Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(LOADER_PREFIX);
        loader.setSuffix(LOADER_SUFFIX);
        handlebars = new Handlebars(loader);
    }

    public static String applyModelInView(String viewPath, Map<String, Object> model) {
        try {
            Template template = handlebars.compile(viewPath);
            return template.apply(model);
        } catch (IOException e) {
            return null;
        }
    }
}
