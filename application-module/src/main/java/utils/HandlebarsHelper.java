package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import dto.ResponseDto;
import java.io.IOException;

public class HandlebarsHelper {

    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public static String apply(String path, ResponseDto responseDto) {
        try {
            Template template = handlebars.compile(path);
            return template.apply(responseDto);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
