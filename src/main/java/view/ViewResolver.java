package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.TemplateRenderingFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.LoggingUtils;

import java.io.IOException;
import java.util.Map;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    private static final String TEMPLATE_DIRECTORY = "/templates/user";
    private static final String HTML = ".html";

    public static String resolve(Map model, String viewName) {
        TemplateLoader loader = new ClassPathTemplateLoader(TEMPLATE_DIRECTORY, HTML);
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("incremented", (Helper<Integer>) (context, options) -> context + 1);

        try {
            Template template = handlebars.compile(viewName);
            return template.apply(model);
        } catch (IOException e) {
            // TODO: 2019-10-07 예외처리, 로거 추가
            LoggingUtils.logStackTrace(logger, e);
            throw new TemplateRenderingFailException();
        }
    }
}
