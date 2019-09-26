package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.ServerErrorException;

import java.io.IOException;

public class HandlebarsGenerator {
    public static byte[] render(ModelAndView modelAndView) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        try {
            Template template = handlebars.compile(modelAndView.getView());
            String userListPage = template.apply(modelAndView.getObjects());
            return userListPage.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(e.getMessage());
        }

    }
}
