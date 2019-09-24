package http.supoort.converter.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.model.response.ServletResponse;

import java.io.IOException;

public class HandleBarViewResolver extends AbstractViewResolver {
    private static final String PREFIX = "/templates";
    private static final String SUFFIX = ".html";

    private Handlebars handlebars;

    public HandleBarViewResolver() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(PREFIX);
        loader.setSuffix(SUFFIX);
        this.handlebars = new Handlebars(loader);
        handlebars.registerHelper("i", (Helper<Integer>) (context, options) -> context + 1);
    }

    @Override
    protected String apply(ServletResponse response) throws IOException {
        String resource = response.getView();
        Template template = handlebars.compile(resource);
        return template.apply(response.getModel());
    }
}
