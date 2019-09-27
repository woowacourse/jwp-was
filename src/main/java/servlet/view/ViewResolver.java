package servlet.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.response.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public static View resolve(final HttpServletResponse httpServletResponse) throws IOException {
        return new View(pageValue(httpServletResponse));
    }

    private static String pageValue(final HttpServletResponse httpServletResponse) throws IOException {
        final String path = httpServletResponse.getResourcePath();
        logger.debug("현재 주소: {}", path);
        final Map<String, Object> model = httpServletResponse.getModel();
        int startIndex = path.indexOf("/");
        int lastIndex = path.lastIndexOf(".");

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(path.substring(lastIndex));
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(path.substring(startIndex + 1, lastIndex));
        Map<String, Object> map = new HashMap<>();

        for (String key : model.keySet()) {
            map.put(key, model.get(key));
        }

        return template.apply(map);
    }
}
