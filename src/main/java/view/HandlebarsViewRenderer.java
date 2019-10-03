package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandlebarsViewRenderer {
    private static final String PREFIX_TEMPLATES = "/templates";
    private static final String SUFFIX_HTML = ".html";

    public static byte[] getBody(String path, Map<String, Object> model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX_TEMPLATES);
        loader.setSuffix(SUFFIX_HTML);
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("increment", (context, options) -> (Integer) context + 1);
        Template template = handlebars.compile(path.substring(0, path.length() - SUFFIX_HTML.length()));
        return template.apply(model).getBytes();
    }
}
