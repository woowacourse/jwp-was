package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TemplateUtils {
    private static final Logger log = LoggerFactory.getLogger(TemplateUtils.class);
    private static final String TEMPLATES_PATH = "/templates";
    private static final String HTML = ".html";

    public static String create(String path, Object object) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PATH);
        loader.setSuffix(HTML);
        Handlebars handlebars = new Handlebars(loader);
        try {
            Template template = handlebars.compile(path);
            return template.apply(object);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("IOException 발생");
        }
    }
}
