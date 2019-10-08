package webserver.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import webserver.message.exception.NotFoundFileException;
import webserver.message.response.Response;

import java.io.IOException;
import java.util.Map;

public class HandleBarView implements View {
    private static final String TEMPLATES_PATH = "/templates";

    private final String path;

    public HandleBarView(final String path) {
        this.path = path;
    }

    @Override
    public void render(Response response, Map<String, ?> models) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PATH);
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (Helper<Integer>) (value, options) -> value + 1);

        try {
            Template template = handlebars.compile(path);
            String page = template.apply(models);
            response.body(page);
        } catch (IOException e) {
            throw new NotFoundFileException();
        }
    }
}
