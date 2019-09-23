package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandlebarResolver {
    private static final String DOT = "\\.";
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    public static String getBody(String path, Map<String, Object> model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);

        String[] tokens = path.split(DOT);

        loader.setSuffix(SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(tokens[0]);

        return template.apply(model);
    }
}
