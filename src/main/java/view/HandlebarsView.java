package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class HandlebarsView implements View {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsView.class);
    private static final String LOADER_PREFIX = "/templates";
    private static final String LOADER_SUFFIX = ".html";

    private static Handlebars handlebars;

    private String viewPath;
    private Map<String, Object> model;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(LOADER_PREFIX);
        loader.setSuffix(LOADER_SUFFIX);
        handlebars = new com.github.jknack.handlebars.Handlebars(loader);
    }

    public HandlebarsView(String viewPath, Map<String, Object> model) {
        this.viewPath = viewPath;
        this.model = model;
    }

    @Override
    public byte[] render() {
        try {
            Template template = handlebars.compile(viewPath);
            String body = template.apply(model);
            if (body != null) {
                return body.getBytes();
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return null;
    }
}
