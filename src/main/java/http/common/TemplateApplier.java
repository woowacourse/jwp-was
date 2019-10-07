package http.common;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.exception.TemplateApplyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TemplateApplier {
    private static final Logger logger = LoggerFactory.getLogger(TemplateApplier.class);

    private static final TemplateLoader templateLoader = new ClassPathTemplateLoader();
    private static final Handlebars handlebars = new Handlebars(templateLoader);
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";

    static {
        templateLoader.setPrefix(TEMPLATE_PREFIX);
        templateLoader.setSuffix(TEMPLATE_SUFFIX);
    }

    public static String apply(String filePath, Object object) {
        String result = "";
        try {
            Template template = handlebars.compile(filePath);
            result = template.apply(object);
        } catch (IOException e) {
            logger.error("template apply error! {}", e.getMessage());
            throw new TemplateApplyException();
        }

        return result;
    }
}