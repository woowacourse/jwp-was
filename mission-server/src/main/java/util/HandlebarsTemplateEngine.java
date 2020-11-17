package util;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import dto.ResponseDto;
import template.TemplateEngine;

public class HandlebarsTemplateEngine implements TemplateEngine {

    private static final Handlebars HANDLEBARS;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        HANDLEBARS = new Handlebars(loader);
    }

    private HandlebarsTemplateEngine() {
    }

    public static HandlebarsTemplateEngine getInstance() {
        return Cache.HANDLEBARS_HELPER;
    }

    @Override
    public String apply(String path, ResponseDto<? extends ResponseDto<?>> responseDto) {
        try {
            Template template = HANDLEBARS.compile(path);
            return template.apply(responseDto);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static class Cache {
        private static final HandlebarsTemplateEngine HANDLEBARS_HELPER = new HandlebarsTemplateEngine();
    }
}
