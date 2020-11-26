package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import java.io.IOException;

public class HandlebarUtils {

    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html"));

    public static String apply(String location, Object object) throws IOException {
        Template template = handlebars.compile(location);
        return template.apply(object);
    }
}
