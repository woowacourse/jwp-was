package web.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import web.model.Model;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.response.HttpStatusCode;

import java.io.IOException;

public class HandlebarView implements View {
    private static final String DEFAULT_PREFIX = "/templates";
    private static final String DEFAULT_SUFFIX = ".html";

    private final String path;
    private final Handlebars handlebars;

    public HandlebarView(String path, TemplateLoader templateLoader) {
        this.path = path;
        this.handlebars = new Handlebars(templateLoader);
    }

    public HandlebarView(String path) {
        this(path, new ClassPathTemplateLoader(DEFAULT_PREFIX, DEFAULT_SUFFIX));
    }

    @Override
    public void render(Model model, HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Template template = this.handlebars.compile(path);
            String view = template.apply(model.getAttributes());
            httpResponse.response(HttpStatusCode.OK, view.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s : 페이지가 존재하지 않습니다.", path));
        }
    }
}
