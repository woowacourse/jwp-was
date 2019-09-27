package servlet.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);
    private static final String START_OF_URL = "/";
    private static final String EXTENSION_DELIMITER = ".";

    public static View resolve(String path) throws IOException {
        return new View(templateView(path));
    }

    private static Template templateView(final String path) throws IOException {
        logger.debug("resource path : {}", path);

        int startIndex = path.indexOf(START_OF_URL);
        int lastIndex = path.lastIndexOf(EXTENSION_DELIMITER);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(path.substring(lastIndex));
        Handlebars handlebars = new Handlebars(loader);

        return handlebars.compile(path.substring(startIndex + 1, lastIndex));
    }
}
