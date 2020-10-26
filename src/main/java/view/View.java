package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class View {
    private static final String EMPTY = "";

    private final String location;

    public View(String location) {
        this.location = location;
    }

    public String render(Map<String, Object> model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setSuffix(EMPTY);
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(location);

        return template.apply(model);
    }
}
