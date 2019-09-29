package controller.core;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String TEMPLATE_SUFFIX = ".html";

    public static String getTemplatePage(final String url) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATE_PREFIX);
        loader.setSuffix(TEMPLATE_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(url);
        Map<String, Collection<User>> users = new HashMap<>();

        users.put("users", DataBase.findAll());
        return template.apply(users);
    }
}
