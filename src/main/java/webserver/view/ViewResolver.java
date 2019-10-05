package webserver.view;

import java.io.IOException;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.utils.ResourcePathUtils;

public class ViewResolver {
    public static byte[] generateDynamicResource(ModelAndView modelAndView) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("");
        loader.setSuffix("");

        String path = ResourcePathUtils.getResourcePath(modelAndView.getViewName());
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(path);
        Map<String, Object> model = modelAndView.getModel();

        return template.apply(model).getBytes();
    }
}
