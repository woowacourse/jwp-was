package webserver.view.template;

import java.io.IOException;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.exception.FailedRenderingException;
import webserver.utils.ResourcePathUtils;
import webserver.view.ModelAndView;

public class HandlebarsHtmlTemplate implements HtmlTemplateEngine {
    @Override
    public byte[] render(ModelAndView modelAndView) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("");
            loader.setSuffix("");

            String path = ResourcePathUtils.getResourcePath(modelAndView.getViewName());
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile(path);
            Map<String, Object> model = modelAndView.getModel();

            return template.apply(model).getBytes();
        } catch (IOException e) {
            throw new FailedRenderingException();
        }
    }
}
