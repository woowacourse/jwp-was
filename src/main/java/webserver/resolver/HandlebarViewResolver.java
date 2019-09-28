package webserver.resolver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.view.HandlebarView;
import webserver.view.View;

import java.io.IOException;

public class HandlebarViewResolver implements Resolver {
    private TemplateLoader loader = new ClassPathTemplateLoader();

    @Override
    public View createView(String viewName) throws IOException {
        HandlebarView view = new HandlebarView(viewName);
        view.setTemplate(createTemplate(viewName));
        return view;
    }

    public Template createTemplate(String viewName) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        return handlebars.compile(viewName);
    }
}
