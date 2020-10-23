package web;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;
import web.application.dto.ResponseDto;

public class HandlebarsHelper {

    public static HandlebarsHelper getInstance() {
        return Cache.HANDLEBARS_HELPER;
    }

    public String apply(String path, ResponseDto responseDto) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        try {
            Template template = handlebars.compile(path);
            return template.apply(responseDto);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static class Cache {

        private static final HandlebarsHelper HANDLEBARS_HELPER = new HandlebarsHelper();
    }
}
