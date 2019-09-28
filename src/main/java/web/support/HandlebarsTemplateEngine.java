package web.support;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.PageNotFoundException;
import webserver.response.ModelAndView;
import webserver.response.TemplateEngine;

import java.io.IOException;

public class HandlebarsTemplateEngine implements TemplateEngine {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarsTemplateEngine.class);

    private Handlebars handlebars;

    public HandlebarsTemplateEngine(String prefix, String suffix) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(prefix);
        loader.setSuffix(suffix);
        this.handlebars = new Handlebars(loader);
        setIndexHelper();
    }

    private void setIndexHelper() {
        handlebars.registerHelper("plusOne", (context, options) -> (Integer) context + 1);
    }

    @Override
    public String render(ModelAndView modelAndView) {
        try {
            Template template = handlebars.compile(modelAndView.getFilePath());
            return template.apply(modelAndView.getModel());
        } catch (IOException | NullPointerException e) {
            logger.error("modelAndView : {}", modelAndView);
            throw new PageNotFoundException(String.format("ModelAndView : %s, 해당 경로의 파일을 찾을 수 없습니다.", modelAndView));
        }
    }
}
