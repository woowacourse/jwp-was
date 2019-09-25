package webserver.support;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandlebarsRenderer {
    private static final String TEMPLATES_PATH = "/templates";
    private static final String HTML_EXTENSION = ".html";

    public static byte[] render(String url, Map<String, Object> model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PATH);
        loader.setSuffix(HTML_EXTENSION);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(url.substring(1));

        return template.apply(model).getBytes();
    }
}
