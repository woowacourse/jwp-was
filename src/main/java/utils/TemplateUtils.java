package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public final class TemplateUtils {
    private static final Logger logger = LoggerFactory.getLogger(TemplateUtils.class);

    private static final String TEMPLATE_FILES_DIRECTORY = "/templates";

    private static final Handlebars handlebars;
    static {
        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_FILES_DIRECTORY);
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public static Optional<String> bake(String path, Object input) {
        try {
            return Optional.of(handlebars.compile(path).apply(input));
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return Optional.empty();
        }
    }
}